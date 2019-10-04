import os
import pygame
from pygame.locals import *
import sys
import random
import numpy as np
from sklearn.neural_network import multilayer_perceptron
from keras.optimizers import Adam
from keras.models import Sequential
from keras.layers.core import Dense, Dropout
from keras.utils import to_categorical

pygame.mixer.init()
pygame.display.set_caption('Drogon  Escape')
##########################################################################################
# WELCOME TO THE WORLD OF GAME OF THRONES-DROGON ESCAPE
# DROGON is stuck in a desert of cactus and trying to escape through narrow passage
# MILESTONE-1
# MARKOV MODEL
# Here we are using markov models for procedural content generation for elements like cactus
# Cactus here are present at above and below the screen, we have offset values ranging from
# -200 to 200, here we add offset values to upper cactus and subtract it from lower cactus
# to give an uneven and challenging look to the obstacles, DROGON needs to be vigilant
# to clear the obstacles.
##########################################################################################
class DrogonEscape1:

    def __init__(self):
        path = os.getcwd()
        trans = {}
        #self.life=5
        self.Q={}
        self.wins=0
        self.flag=True
        self.MOVE_UP=1
        self.MOVE_DOWN=2
        self.POSSIBLE_MOVES=9
        self.LEARNING_RATE = 0.000001;
        self.DISCOUNT_FACTOR = 0.3;
        f1 = open("offsetData.txt", "r")
        for line in f1.readlines():
            line = line.replace("\n", "")
            s = line.split(",")
            for j in range(len(s) - 1):
                t = ",".join(s[j:j + 2])
                trans.setdefault(t, 0)
                trans[t] += 1

        self.index = {}
        self.index.setdefault(-100, 0)
        self.index.setdefault(-80, 1)
        self.index.setdefault(-60, 2)
        self.index.setdefault(-40, 3)
        self.index.setdefault(-20, 4)
        self.index.setdefault(0, 5)
        self.index.setdefault(20, 6)
        self.index.setdefault(40, 7)
        self.index.setdefault(60, 8)
        self.index.setdefault(80, 9)
        self.index.setdefault(100, 10)

        self.a = [];
        self.O = [];
        i = 11;
        j = 11;

        # Preparing the observation matrix
        # The observation matrix provides  the emission probability for a given state
        for x in range(0, i):
            yColumn=[];
            for y in range(0, j):
                # if x lies within 7
                if x <= 7:
                    if y==x:
                        yColumn.append(0.7)
                    elif y==x-1 or y==x+1:
                        yColumn.append(0.15)
                    elif y==8:
                        yColumn.append(0.04)
                    else:
                        yColumn.append(0.01)
                # if x is either 9 or 10
                elif (x == 9 or x == 10):
                    if y==9 or y==10:
                        yColumn.append(1)
                    else:
                        yColumn.append(0)
                else:
                    yColumn.append(1/11);
            self.O.append(yColumn)
        # Tranposition matrix
        # It gives probabilities of transition from one offset to another (-200,200)
        # a[i][j] gives probability of transition from initial state j to next state i
        # The data for offset is taken from thousands of values of offsets present in
        # offsetData.txt file. Here Markov Model is a bigram where current state is dependent
        # on previous state
        for i in range(11):
            new = []
            for j in range(11):
                new.append(0)
            self.a.append(new)
        for k in trans:
            s = k.split(",")
            j = self.index.get(int(s[0]))
            i = self.index.get(int(s[1]))
            self.a[i][j] = trans.get(k)

        sumAll = []
        for j in range(11):
            s = 0
            for i in range(11):
                s += (self.a[i][j])
            sumAll.append(s)
        for j in range(11):
            for i in range(11):
                if (sumAll[j] != 0):
                    self.a[i][j] = (self.a[i][j] / sumAll[j])
                else:
                    self.a[i][j] = 0


        self.screen = pygame.display.set_mode((400, 600))
        self.drogon = pygame.Rect(65, 50, 40, 30)
        # loading images
        self.background = pygame.image.load("assets/back.png").convert()
        self.birdSprites = [pygame.image.load("assets/flying.png").convert_alpha(),
                            pygame.image.load("assets/flying.png").convert_alpha(),
                            pygame.image.load("assets/dead.png")]
        self.wallUp = pygame.image.load("assets/cactus.png").convert_alpha()
        self.wallDown = pygame.image.load("assets/cactus1.png").convert_alpha()
        self.gap = 140 # fixed cactus gap
        self.wallx = 400
        self.drogonY = 350
        self.drogonX=65
        self.jump = 0
        self.jumpSpeed = 10
        self.gravity = 5
        self.dead = False
        self.sprite = 0
        self.counter = 0
        self.offset = random.randrange(-100, 100,20)
        self.reward=0
        self.best_action=self.MOVE_UP
        self.env=[]
        self.X_data=[]
        self.Y_data=[]
        self.memory=[]
        self.gamma=self.DISCOUNT_FACTOR
        self.model=self.network()
        self.learning_rate = self.LEARNING_RATE

        for i in range(3):
            a = []
            for j in range(2):
                a.append(0)
            self.env.append(a)

    # Generate offset Sequence
    # Here we are using Markov Model to generate sequence of offsets(Procedural Content Generation), we are using one offset at a time
    # and removing it once its used. More offset Sequences are created once previous sequences are consumed.
    def generateSequence(self,generatedMap):
            P = 0;
            self.index.setdefault( 0,-200)
            self.index.setdefault(1,-160)
            self.index.setdefault(2,-120)
            self.index.setdefault(3,-80)
            self.index.setdefault(4,-40)
            self.index.setdefault(5,0)
            self.index.setdefault(6,40)
            self.index.setdefault(7,80)
            self.index.setdefault(8,120)
            self.index.setdefault(9,160)
            self.index.setdefault(10,200)
            if len(generatedMap) == 0:
                endState = 10;
                state = 0;
                maxProb = 0;
                while state != endState:
                    randProb=random.choice(self.O[state])
                    interState=self.O[state].index(randProb,0,10)
                    maxProb = max(self.a[interState]);
                    P = P + maxProb;
                    stateList = [];
                    stateList = self.a[interState];
                    nextState = stateList.index(maxProb,0,10);
                    state = nextState;
                    generatedMap.append(state)
                    if P > 1:
                        break
            self.count = len(generatedMap)
            offsetIndex=generatedMap[self.count-1]
            self.offset = self.index.get(offsetIndex)
            generatedMap = generatedMap.remove(offsetIndex)
            self.count -= 1;

    # Models
    # Layers
    #
    def network(self, weights=None):
        model = Sequential()
        model.add(Dense(output_dim=120, activation='relu', input_dim=1))
        model.add(Dropout(0.15))
        model.add(Dense(output_dim=120, activation='relu'))
        model.add(Dropout(0.15))
        model.add(Dense(output_dim=120, activation='relu'))
        model.add(Dropout(0.15))
        model.add(Dense(output_dim=10, activation='softmax'))
        opt = Adam(self.LEARNING_RATE)
        model.compile(loss='mse', optimizer=opt)

        if weights:
            model.load_weights(weights)
        return model

    # Memorize
    #
    #
    def remember(self, state, action, reward, next_state, done):
        self.memory.append((state, action, reward, next_state, done))

    # Replay memory
    # LSTM
    def replay_new(self, memory):
        if len(memory) > 32:
            minibatch = random.sample(memory, 32)
        else:
            minibatch = memory
        for state, action, reward, next_state, done in minibatch:
            target = reward
            if not done:
                target = reward + self.gamma * np.amax(self.model.predict(np.array([next_state]))[0])

            target_f = self.model.predict(np.array([state]))
            target_f[0][np.argmax(action)] = target
            self.model.fit(np.array([state]), target_f, epochs=2, verbose=0)


    #
    #
    #
    def train_short_memory(self, state, action, reward, next_state, done):
        target = reward
        if not done:
            target = reward + self.gamma * np.amax(self.model.predict(next_state)[0])
        target_f = self.model.predict([state])
        target_f[0][np.argmax(action)] = target
        self.model.fit([state], target_f, epochs=2, verbose=0)

    # Procedural content generation
    def updateWalls(self,generatedMap,SOUNDS):
        offsetList=[];
        self.wallx -= 2
        if self.wallx < -80:
            self.wallx = 400
            self.counter += 1
            self.wins += 1
            if (self.dead):
                self.reward = -10
            elif(not self.dead):
                self.reward = 20

            #SOUNDS['point'].play()
            self.generateSequence(generatedMap)


    def drogonUpdate(self,font):
        if self.jump:
            self.jumpSpeed -= 1
            self.drogonY -= self.jumpSpeed
            self.jump -= 1
        else:
            self.drogonY += self.gravity
            self.gravity += 0.2
        self.drogon[1] = self.drogonY
        upRect = pygame.Rect(self.wallx,
                             360 + self.gap - self.offset + 10,
                             self.wallUp.get_width() - 10,
                             self.wallUp.get_height())
        downRect = pygame.Rect(self.wallx,
                               0 - self.gap - self.offset - 10,
                               self.wallDown.get_width() - 10,
                               self.wallDown.get_height())
        #print(self.env)
        self.new_env = self.getNearBySquares(upRect, downRect)
        #print(new_env)
        envCode = self.getEnvironmentCode(self.new_env)
        best_move=self.best_action
        #print(best_move)

        self.updateQ(best_move, envCode)



        #self.clf.fit(self.X_data, self.Y_data)



        #print(envCode)
        if upRect.colliderect(self.drogon):
            self.dead = True

        if downRect.colliderect(self.drogon):
            self.dead = True


        if not 0 < self.drogon[1] < 600:
            # if self.drogon[1]>600:
            #  self.dead=True
            self.reward=-20
            self.drogon[1] = 50
            self.drogonY = 50
            self.dead = False
            #self.counter = 0
            self.wallx = 400
            self.offset = random.randrange(-100, 100,20)
            self.gravity = 5
        else:
            self.reward=5

        # if self.dead and self.flag:
        #     self.life-=1
        #     self.flag=False
        #
        # if not self.dead:
        #     self.flag=True
        # if(self.life==0):
        #     self.life==5
        self.env = self.new_env

    def run(self):
        path = ""
        # Sounds for various stages of the game
        SOUNDS = {}
        if 'win' in sys.platform:
            soundExt = '.wav'
        else:
            soundExt = '.ogg'

        SOUNDS['die'] = pygame.mixer.Sound(path + 'assets/audio/die' + soundExt)
        SOUNDS['hit'] = pygame.mixer.Sound(path + 'assets/audio/hit' + soundExt)
        SOUNDS['point'] = pygame.mixer.Sound(path + 'assets/audio/point' + soundExt)
        SOUNDS['swoosh'] = pygame.mixer.Sound(path + 'assets/audio/swoosh' + soundExt)
        SOUNDS['wing'] = pygame.mixer.Sound(path + 'assets/audio/wing' + soundExt)
        clock = pygame.time.Clock()
        generatedMap = [];
        pygame.font.init()
        font = pygame.font.SysFont("Arial", 50)

        while True:
            clock.tick(60)
            flag=False
            self.best_action = self.decideMove()
            #if (self.drogonY > 500):
                #print(self.drogonY)
                #print(self.best_action)
                #print(">>>>>>>>>>>>>>>>")
            if self.best_action == self.MOVE_UP  and not self.dead:
                self.jump = 17
                self.gravity = 5
                self.jumpSpeed = 10
            elif self.best_action == self.MOVE_DOWN  and not self.dead:
                self.jump = -10
                self.gravity = 0  # 5
                self.jumpSpeed = -10
                #SOUNDS['wing'].play()
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    sys.exit()
            # for event in pygame.event.get():
            #     if event.type == pygame.QUIT:
            #         sys.exit()
            #     if (event.type == pygame.KEYDOWN or event.type == pygame.MOUSEBUTTONDOWN) and not self.dead:
            #         self.jump = 17
            #         self.gravity = 5
            #         self.jumpSpeed = 10
            #         self.drogonX+=5
            #         SOUNDS['wing'].play()
            #print("drogonX:   " + str(self.drogonX))
            #print("drogonY:   " + str(self.drogonY))
            self.screen.fill((255, 255, 255))
            self.screen.blit(self.background, (0, 0))
            self.screen.blit(self.wallUp,
                             (self.wallx, 360 + self.gap - self.offset))
            self.screen.blit(self.wallDown,
                             (self.wallx, 0 - self.gap - self.offset))
            self.screen.blit(font.render(str(self.counter),
                                         -1,
                                         (255, 255, 255)),
                             (200, 50))
            # check if the dragon is dead
            if self.dead:
                self.sprite = 2
                #SOUNDS['hit'].play()
                #SOUNDS['die'].play()


            elif self.jump:
                self.sprite = 1
            self.screen.blit(self.birdSprites[self.sprite], (70, self.drogonY))
            if not self.dead:
                self.sprite = 0
            self.updateWalls(generatedMap,SOUNDS) # Markov Model here
            self.drogonUpdate(font)
            pygame.display.update()
            self.replay_new(self.memory)


    def getNearBySquares(self,upRect,downRect):
        env=[]
        for i in range(3):
            a=[]
            for j in range(2):
                a.append(0)
            env.append(a)
        recAbove = pygame.Rect(65,self.drogonY - 30, 40, 30)
        recBelow = pygame.Rect(65,self.drogonY + 30,40,30)
        recForwardA=pygame.Rect(105,self.drogonY-30,40,30)
        recForward = pygame.Rect(105, self.drogonY, 40, 30)
        recForwardB = pygame.Rect(105, self.drogonY+30, 40, 30)
        for i in range(3):
            for j in range(2):
               if(i==0 and j==0):
                   if(recAbove.colliderect(upRect) or recAbove.colliderect(downRect)):
                    env[0][0]=1
                   else: env[0][0]=0
               if(i==0 and j==1):
                   if(recForwardA.colliderect(upRect) or recForwardA.colliderect(downRect)):
                    env[0][1] = 1
                   else:
                       env[0][1] = 0
               if (i ==1 and j ==1):
                   if(recForward.colliderect(upRect) or recForward.colliderect(downRect)):
                    env[1][1] = 1
                   else:
                       env[1][1] = 0
               if (i==2 and j==0):
                   if(recBelow.colliderect(upRect) or recBelow.colliderect(downRect)):
                    env[2][0] = 1
                   else:
                       env[2][0] = 0

               if(i==2 and j==1):
                   if(recForwardB.colliderect(upRect) or recForwardB.colliderect(downRect)):
                    env[2][1] = 1
                   else:
                       env[2][1] = 0
        return env

    def getEnvironmentCode(self,env):
        return ((env[0][0] * 128) + (env[0][1] * 64) + (env[1][1] * 32) + (env[2][0] + 16) + (env[2][1] * 8))

    def getLearningQ(self, environment, best_move):

        currQ = []
        if (len(self.Q) > 0):
            for key in self.Q.keys():
                if environment == key:
                    currQ = self.Q.get(environment)

        if (currQ.__len__() is not 0):
            return currQ[best_move]

        else:
            return 1

    def getBestAction(self, env):
        bestQValue = float("-inf")
        bestAction = 2
        for i in range(self.POSSIBLE_MOVES):

            val = self.getLearningQ(env, i)

            if (val > bestQValue):
                bestQValue = val
                bestAction = i


        # predict action based on the old state
        prediction = self.model.predict([self.getEnvironmentCode(env)])
        # print(prediction)
        final_move = to_categorical(np.argmax(prediction[0]), num_classes=10)
        bestAction=(np.argmax(final_move))

        return bestAction


    # Update the q values here
    def updateQ(self, best_move, new_environment):

        # currentQ = self.getLearningQ(self.getEnvironmentCode(self.env), best_move)
        # bestNextMove = self.getBestAction(new_environment)
        # bestNextQ = self.getLearningQ(new_environment, bestNextMove);
        # newQ = (1 - self.LEARNING_RATE) * currentQ + self.LEARNING_RATE * (
        #         self.reward + self.DISCOUNT_FACTOR * bestNextQ);
        actions = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        # if self.Q.get(self.getEnvironmentCode(self.env)) is not None:
        #     actions = self.Q.get(self.getEnvironmentCode(self.env))
        #     actions[best_move] = newQ
        #
        # else:
        #
        #     actions[best_move] = newQ
        #     self.Q[self.getEnvironmentCode(self.env)] = actions
        #     self.X_data.append([self.getEnvironmentCode(self.env),best_move,new_environment])
        #     self.Y_data.append(np.array(actions))
        for i in range(9):
            if best_move==i:
                actions[i]=1

        # train short memory base on the new action and state
        self.train_short_memory(self.getEnvironmentCode(self.env), actions, self.reward, [new_environment], self.dead)
        self.remember(self.getEnvironmentCode(self.env), actions, self.reward, [new_environment], self.dead)




    def decideMove(self):
        rho=0.8/(self.wins+1)
        #print(self.wins)
        explore = random.uniform(0,1)
        #print(explore)
        if (self.drogonY > 500 and self.drogonY < 550):
            return 1
        if (explore< rho):

            return random.randint(1, 9)
        else: return self.getBestAction(self.env)
if __name__ == "__main__":
    DrogonEscape1().run()