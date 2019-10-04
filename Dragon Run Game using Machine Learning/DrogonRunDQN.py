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
#YOUTUBE LINK------https://youtu.be/x7Rew1-hLCg

##########################################################################################
# WELCOME TO THE WORLD OF GAME OF THRONES-DROGON ESCAPE
# DROGON is stuck in a desert of cactus and trying to escape through narrow passage
# MILESTONE-2
# Using Q learning with Neural Network
# We have used long short-term memory(LSTM) Recurrent Neural Network with Q learning
# We have used batch size of 10 to make the simulation faster and smoother
# MILESTONE-1
# MARKOV MODEL
# Here we are using markov models for procedural content generation for elements like cactus
# Cactus here are present at above and below the screen, we have offset values ranging from
# -200 to 200, here we add offset values to upper cactus and subtract it from lower cactus
# to give an uneven and challenging look to the obstacles, DROGON needs to be vigilant
# to clear the obstacles.
##########################################################################################
class DrogonEscape:

    def __init__(self):
        self.screech=0
        path = os.getcwd()
        trans = {}
        self.life = 0
        self.Q = {}
        self.wins = 0
        self.flag = True
        self.MOVE_UP = 1
        self.MOVE_DOWN = 2
        self.POSSIBLE_MOVES = 8
        self.LEARNING_RATE = 0.000001;
        self.DISCOUNT_FACTOR = 0.3;

        #################################################################################################
        # loading data from Offset.txt file
        #################################################################################################
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
        self.scroll = 0

        self.a = [];
        self.O = [];
        i = 11;
        j = 11;

        ################################################################################################
        # Preparing the observation matrix
        # The observation matrix provides  the emission probability for a given state
        ################################################################################################
        for x in range(0, i):
            yColumn = [];
            for y in range(0, j):
                # if x lies within 7
                if x <= 7:
                    if y == x:
                        yColumn.append(0.7)
                    elif y == x - 1 or y == x + 1:
                        yColumn.append(0.15)
                    elif y == 8:
                        yColumn.append(0.04)
                    else:
                        yColumn.append(0.01)
                # if x is either 9 or 10
                elif (x == 9 or x == 10):
                    if y == 9 or y == 10:
                        yColumn.append(1)
                    else:
                        yColumn.append(0)
                else:
                    yColumn.append(1 / 11);
            self.O.append(yColumn)

        ##############################################################################################
        # Tranposition matrix
        # It gives probabilities of transition from one offset to another (-200,200)
        # a[i][j] gives probability of transition from initial state j to next state i
        # The data for offset is taken from thousands of values of offsets present in
        # offsetData.txt file. Here Markov Model is a bigram where current state is dependent
        # on previous state
        ##############################################################################################
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
        self.drogon = pygame.Rect(65, 50, 30, 40)
        # loading images
        self.background = pygame.image.load("assets/back.png").convert_alpha()
        self.birdSprites = [pygame.image.load("assets/flying.png").convert_alpha(),
                            pygame.image.load("assets/flapping.png").convert_alpha(),
                            pygame.image.load("assets/dead.png").convert_alpha(),
                            pygame.image.load("assets/img1.png").convert_alpha(),
                            pygame.image.load("assets/img2.png").convert_alpha(),
                            pygame.image.load("assets/img3.png").convert_alpha(),
                            pygame.image.load("assets/img4.png").convert_alpha(),
                            pygame.image.load("assets/img5.png").convert_alpha()]
        self.wallUp = pygame.image.load("assets/cactus.png").convert_alpha()
        self.wallDown = pygame.image.load("assets/cactus1.png").convert_alpha()
        # base (ground) sprite
        self.base = pygame.image.load('assets/base.png').convert_alpha()

        pygame.mixer.music.fadeout(200)

        self.gap = 140  # fixed cactus gap
        self.wallx = 400
        self.drogonY = 350
        self.drogonX = 65
        self.jump = 0
        self.jumpSpeed = 10
        self.gravity = 5
        self.dead = False
        self.sprite = 0
        self.counter = 0
        self.offset = random.randrange(-100, 100, 20)
        self.reward = 0
        self.best_action = self.MOVE_UP
        self.env = []
        self.X_data = []
        self.Y_data = []
        self.memory = []
        self.gamma = self.DISCOUNT_FACTOR
        self.model = self.network()

        #############################################################################################
        # Load weights from a pretrained model instead-"weights.hdf5" contained pretrained weigths
        # uncomment below line to load data from weights.hdf5 file
        #############################################################################################
        self.model = self.network("weights.hdf5")
        self.learning_rate = self.LEARNING_RATE

        # initializing environment
        for i in range(5):
            a = []
            for j in range(2):
                a.append(0)
            self.env.append(a)

    #######################################################################################################
    # Generate offset Sequence
    # Here we are using Markov Model to generate sequence of offsets(Procedural Content Generation), we are using one offset at a time
    # and removing it once its used. More offset Sequences are created once previous sequences are consumed.
    ########################################################################################################
    def generateSequence(self, generatedMap):
        P = 0;
        self.index.setdefault(0, -200)
        self.index.setdefault(1, -160)
        self.index.setdefault(2, -120)
        self.index.setdefault(3, -80)
        self.index.setdefault(4, -40)
        self.index.setdefault(5, 0)
        self.index.setdefault(6, 40)
        self.index.setdefault(7, 80)
        self.index.setdefault(8, 120)
        self.index.setdefault(9, 160)
        self.index.setdefault(10, 200)
        if len(generatedMap) == 0:
            endState = 10;
            state = 0;
            maxProb = 0;
            while state != endState:
                randProb = random.choice(self.O[state])
                interState = self.O[state].index(randProb, 0, 10)
                maxProb = max(self.a[interState]);
                P = P + maxProb;
                stateList = [];
                stateList = self.a[interState];
                nextState = stateList.index(maxProb, 0, 10);
                state = nextState;
                generatedMap.append(state)
                if P > 1:
                    break
        self.count = len(generatedMap)
        offsetIndex = generatedMap[self.count - 1]
        self.offset = self.index.get(offsetIndex)
        generatedMap = generatedMap.remove(offsetIndex)
        self.count -= 1;

    ###############Neural Network#######################################################
    # We are using LSTM-Recurrent Neural Network
    # The below neural network  uses three hidden layers
    # we are using keras library Adam Optimizer and learning rate is kept as 0.0001
    ####################################################################################
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

    ###################################################################################################
    # Memorize
    # Here we are memorizing and storing the states along with the reward and action
    ####################################################################################################
    def remember(self, state, action, reward, next_state, done):
        self.memory.append((state, action, reward, next_state, done))

    ####################################################################################################
    # Replay memory
    # LSTM
    # Here we are taking Q learning data in batches and fitting the data
    ####################################################################################################
    def replay_new(self, memory):
        if len(memory) > 16:
            minibatch = random.sample(memory, 16)
        else:
            minibatch = memory
        for state, action, reward, next_state, done in minibatch:
            target = reward
            if not done:
                target = reward + self.gamma * np.amax(self.model.predict(np.array([next_state]))[0])

            target_f = self.model.predict(np.array([state]))
            target_f[0][np.argmax(action)] = target
            self.model.fit(np.array([state]), target_f, epochs=2, verbose=0)

    #####################################################################################################
    # LSTM part of neural network
    # After fitting the training data, actions are predicted and again predicted data is fitted into LSTM
    ######################################################################################################
    def train_short_memory(self, state, action, reward, next_state, done):
        target = reward
        if not done:
            target = reward + self.gamma * np.amax(self.model.predict(next_state)[0])
        target_f = self.model.predict([state])
        target_f[0][np.argmax(action)] = target
        self.model.fit([state], target_f, epochs=2, verbose=0)

    ################################################################################################
    # Procedural content generation
    # update wallx for cactus, wins and gamepoints here
    ################################################################################################
    def updateWalls(self, generatedMap, SOUNDS):
        offsetList = [];
        self.wallx -= 2
        if (self.dead):
            self.reward = -10
        if self.wallx < -80:
            self.wallx = 400
            self.counter += 1
            self.wins += 1

            if (not self.dead):
                self.reward = 20

            SOUNDS['drogon'].play()
            self.sprite=7
            self.screech=5

            # Generate a offset sequence using Markov Models
            self.generateSequence(generatedMap)

    ######################################################################################
    # drogon update is done here
    # new environment is generated after the move is decided and Q values are updated
    # position of cactus are also updated here with the new offset
    ######################################################################################
    def drogonUpdate(self, font):
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
        self.new_env = self.getNearBySquares(upRect, downRect)
        envCode = self.getEnvironmentCode(self.new_env)
        best_move = self.best_action

        self.updateQ(best_move, envCode)

        if upRect.colliderect(self.drogon):
            self.dead = True

        if downRect.colliderect(self.drogon):
            self.dead = True

        # reset parameters and rewards to below values if drogon moves out of window
        if not 0 < self.drogon[1] < 600:
            self.reward = -20
            self.drogon[1] = 50
            self.drogonY = 50
            self.dead = False
            self.wallx = 400
            self.offset = random.randrange(-100, 100, 20)
            self.gravity = 5
        else:
            self.reward = 5

        self.env = self.new_env
    ####################################################################################################
    # Driver function for the animation
    ####################################################################################################
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
        SOUNDS['drogon'] = pygame.mixer.Sound(path + 'assets/audio/drogon' + soundExt)
        SOUNDS['swoosh'] = pygame.mixer.Sound(path + 'assets/audio/swoosh' + soundExt)
        SOUNDS['wing'] = pygame.mixer.Sound(path + 'assets/audio/wing' + soundExt)
        SOUNDS['GOT'] = pygame.mixer.Sound(path + 'assets/audio/Game of Thrones' + soundExt)
        clock = pygame.time.Clock()
        generatedMap = [];
        pygame.font.init()
        font = pygame.font.SysFont("Arial", 50)
        SOUNDS['GOT'].set_volume(0.7)
        SOUNDS['GOT'].play(10)

        while True:
            clock.tick(60)
            flag = False
            self.best_action = self.decideMove()
            if self.best_action == self.MOVE_UP and not self.dead:
                self.jump = 17
                self.gravity = 5
                self.jumpSpeed = 10
                SOUNDS['wing'].play()
            elif self.best_action == self.MOVE_DOWN and not self.dead:
                self.jump = -10
                self.gravity = 0
                self.jumpSpeed = -10
                SOUNDS['swoosh'].play()

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    sys.exit()

            self.screen.fill((255, 255, 255))
            self.screen.blit(self.background, (self.scroll, 0))
            self.screen.blit(self.background, (400 + self.scroll, 0))
            self.scroll -= 1
            if self.scroll == -400:
                self.scroll = 0
            self.screen.blit(self.wallUp,
                             (self.wallx, 360 + self.gap - self.offset))
            self.screen.blit(self.wallDown,
                             (self.wallx, 0 - self.gap - self.offset))
            self.screen.blit(font.render(str(self.counter),
                                         -1,
                                         (255, 255, 255)),
                             (200, 50))
            # check if the dragon is dead
            if self.dead and self.flag:
                self.life += 1
                self.flag = False

            if not self.dead:
                self.flag = True

            if self.dead:
                self.sprite = 2
                # SOUNDS['hit'].play()
                SOUNDS['die'].play()
            else:
                if not self.screech>0:
                 if self.jump > 0:
                    self.sprite = 3

                 else:
                    self.sprite = 0
                else: self.screech-=1

            self.screen.blit(self.birdSprites[self.sprite], (70, self.drogonY))
            self.updateWalls(generatedMap, SOUNDS)  # Markov Model here
            self.drogonUpdate(font)
            pygame.display.update()
            self.replay_new(self.memory)

            # Store to the weights file if it has gained more than 5 points in the game
            if self.counter > 5:
                self.model.save_weights('weights.hdf5')
            print(self.life)

    ###################################################################################################
    # collision check in environment of size 5X2 with drogon at env[2][0]
    # collision with upper cactus and lower cactus is checked with every env[i][j]
    ##################################################################################################
    def getNearBySquares(self, upRect, downRect):
        env = []
        for i in range(5):
            a = []
            for j in range(2):
                a.append(0)
            env.append(a)
        recAbove2 = pygame.Rect(65, self.drogonY - 100, 84, 50)
        recAbove1 = pygame.Rect(65, self.drogonY - 50, 84, 50)
        recBelow1 = pygame.Rect(65, self.drogonY + 50, 84, 50)
        recBelow2 = pygame.Rect(65, self.drogonY + 100,84 , 50)
        recForwardA1 = pygame.Rect(149, self.drogonY - 100, 84, 50)
        recForwardA = pygame.Rect(149, self.drogonY - 50, 84, 50)
        recForward = pygame.Rect(149, self.drogonY, 84, 84)
        recForwardB = pygame.Rect(149, self.drogonY + 50, 84, 50)
        recForwardB1 = pygame.Rect(149, self.drogonY + 100, 84, 50)
        for i in range(5):
            for j in range(2):
                if (i == 0 and j == 0):
                    if (recAbove2.colliderect(upRect) or recAbove2.colliderect(downRect)):
                        env[0][0] = 1
                    else:
                        env[0][0] = 0
                if (i == 0 and j == 1):
                    if (recForwardA1.colliderect(upRect) or recForwardA1.colliderect(downRect)):
                        env[0][1] = 1
                    else:
                        env[0][1] = 0
                if (i == 1 and j == 0):
                    if (recAbove1.colliderect(upRect) or recAbove1.colliderect(downRect)):
                        env[1][0] = 1
                    else:
                        env[1][0] = 0
                if (i == 1 and j == 1):
                    if (recForwardA.colliderect(upRect) or recForwardA.colliderect(downRect)):
                        env[1][1] = 1
                    else:
                        env[1][1] = 0
                if (i == 2 and j == 1):
                    if (recForward.colliderect(upRect) or recForward.colliderect(downRect)):
                        env[2][1] = 1
                    else:
                        env[2][1] = 0
                if (i == 3 and j == 0):
                    if (recBelow1.colliderect(upRect) or recBelow1.colliderect(downRect)):
                        env[3][0] = 1
                    else:
                        env[3][0] = 0
                if (i == 3 and j == 1):
                    if (recForwardB.colliderect(upRect) or recForwardB.colliderect(downRect)):
                        env[3][1] = 1
                    else:
                        env[3][1] = 0
                if (i == 4 and j == 0):
                    if (recBelow2.colliderect(upRect) or recBelow2.colliderect(downRect)):
                        env[4][0] = 1
                    else:
                        env[4][0] = 0
                if (i == 4 and j == 1):
                    if (recForwardB1.colliderect(upRect) or recForwardB1.colliderect(downRect)):
                        env[4][1] = 1
                    else:
                        env[4][1] = 0
        return env

    ####################################################################################################
    # get unique environment code-There are 2^9=2048 different environments
    ####################################################################################################
    def getEnvironmentCode(self, env):
        return ((env[0][0] * 2048) + (env[0][1] * 1024) + (env[1][0] * 512)+(env[1][1] * 256) + (env[2][1] + 128) + (env[3][0] * 64) +(env[3][1] * 32)+(env[4][0] * 16)+(env[4][1] * 8))

    ####################################################################################################
    # get best action based on LSTM and Q values fed to LSTM
    ####################################################################################################
    def getBestAction(self, env):
        bestQValue = float("-inf")
        bestAction = 1  # default best action

        # predict action based on the old state
        prediction = self.model.predict([self.getEnvironmentCode(env)])
        final_move = to_categorical(np.argmax(prediction[0]), num_classes=10)
        bestAction = (np.argmax(final_move))

        return bestAction

    ####################################################################################################
    # Update the q values here
    ####################################################################################################
    def updateQ(self, best_move, new_environment):
        actions = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
        for i in range(1,10):
            if best_move == i:
                actions[i] = 1

        # train short memory base on the new action and state
        self.train_short_memory(self.getEnvironmentCode(self.env), actions, self.reward, [new_environment], self.dead)
        self.remember(self.getEnvironmentCode(self.env), actions, self.reward, [new_environment], self.dead)

    ##############################################################################################################
    #decide moves-Q Learning
    ##############################################################################################################
    def decideMove(self):
        rho = 0.8 / (self.wins + 1)
        explore = random.uniform(0, 1)
        #condition to avoid drogon to move out of window and avoid gravity
        if (self.drogonY > 475 and self.drogonY < 525):
            return 1
        if (explore < rho):
            return random.randint(1, self.POSSIBLE_MOVES)
        else:
            return self.getBestAction(self.env)


if __name__ == "__main__":
    DrogonEscape().run()
