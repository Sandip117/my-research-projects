# ABOUT THIS PROJECT
    This project contains submission of Sandip Samal & Nirupama Sharma for Game AI Project: Milestone 1 & Milestone 2


# ABOUT THIS GAME
    The name of the game is 'Drogon Escape'
    The theme of the game is motivated from the popular HBO TV series "Game of Thrones".
    "Drogon" was one of the dragon of Queen "Khaleese". In this game, a dragon "Drogon", is
    trapped in a desert set up and the player's aim is to help the dragon escape from obstacles.
    The obstacles are dry and thorny cactus, and "Drogon" has to fly through them against gravity.

    Any key press or mouse click helps "Drogon" to elevate itself in the game. The motion of the dragon
    is in forward direction and cannot be changed. Every escape increases the score by +1 and hitting
    on any obstacles kills the dragon instantly. The future code will contain an energy level for the
    dragon to survive a certain number of deaths before the game ends, and additional game levels
    with higher difficulty . The goal of the game is to maximize the score with minimum loss of lives.


# HOW TO PLAY THIS GAME
    The operation of the game is very simple. Just download the folder and go inside it. Then
    type $python DrogonRunDQN.py. Make sure you install pygame and python v3.0 or above in your system.
    It will be very convenient if you have pyCharm installed in your system.
    Install keras and other prompted libraries in the game

# MODELS USED
    We have implemented Markov Models for map generation of the game. The position of the obstacles changes
    on every tick and this is handles by Markov Chains. We hand crafted our feature data for training the
    model. We later on created our Transition & Observation matrices from the training data set.

    We are using simple content generation for generating a sequence of offset values (position of obstacles) .
    We have implemented a decay factor of 1 for random exploration of states from the observation matrix.
    Next states are selected on the conditional probability of the Transition matrix.

# MILESTONE 2
    The next steps are to enable the dragon to navigate the game space by itself by learning (using Q learning and
     a recurrent Neural Network).

     To view the network learning click here:- https://youtu.be/bUu_Rf-kIjI

     # INITIAL EXPERIMENTS
        We experimented with a lots of parameters and models here
        Our initial attempt was very simple and straight forward. We used Q-learning algorithm along with
        Bellman equation to train the dragon on best actions to a particular state/environment , based on
        their q-values. This worked pretty well except the learning mechanism was not smooth enough during the
        transition of states. The  behavior of the 'Drogon' did improve , but was still hitchy.

     # LATER IMPROVEMENTS
        Later on, we switched to single pass Neural Network and tried to train the NPC after every transition
        of states and predicted a q-value from the network instead , for the best action. This was an improvement
        on the earlier model but failed to accommodate a lot of transition of states data. The model could
        not update itself based on new events in the game.

     # FINAL IMPROVEMENTS
        Finally we arrived at a conclusion to implement a recurring neural network instead. We used 4 layers of relu
        layer in a Neural Network. We used the concept of LSTM or Long Short Term Memory to train the model with
        a mini batch of observations. We initially used a batch size of 1000 state transitions to train our
        model which was extremely slow. Later on we decreased it to 32 and decreased the learning rate to 0.000001
        instead. This really helped us to train the model effectively while still striking the right balance
        between exploration and exploitation.

        We have also used back propagation based on the discounted reward gained while transitioning the states.
        The goal of this step is to maximize the reward value for a particular action in a state.

     # SOME MODEL RELATED JARGONS

        Episodes: An episode is a series of transition of states and best actions taken in the state, till
        the series terminates. A terminal state is defined when the dragon dies in the game.

        Mini Batch: It is a sampled batch of episodes that is used to train our model at a particular time.
        This helps in reducing the noise in the model while iteratively training it with new data.

        LSTM: This is basically an observation process where the model observes a series of played
        states till it reaches a terminal states and based on that data , learns the best action for a
        particular environment. It helps in smoothening the learning process.

        Pre-trained model: To reduce the learning time in the game, we are storing a pretrained model
        whenever the dragon gains at least 6 points. We have put comments in the init_() section which
        can be removed to train the model from scratch.

# FUTURE GOALS- Final Submission
    The next steps will be to work on the UI of the game. Though it is a simple game, we have identified
    some of the regions where we can improve our UI. For example, animating the dragon using a gif instead
    of still might be one.
    We will also try to play around with some of the model parameters used in the game for our
    evaluation purpose and submit an observation stats for the same.
