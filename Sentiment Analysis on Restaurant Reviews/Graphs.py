import matplotlib.pyplot as plt

neurons=range(45,50)

plt.plot(neurons,[0.78800, 0.7808965, 0.7812653, 0.7798345, 0.7798345])
plt.plot(neurons,[0.73800, 0.7808965, 0.7412653, 0.7798345, 0.7798345])
plt.plot(neurons,[0.78800, 0.7808965, 0.7812653, 0.7798345, 0.7798345])
plt.plot(neurons,[0.78800, 0.7808965, 0.7812653, 0.7798345, 0.7798345])

plt.legend(['relu','linear','logistic','tanh'])
plt.xlabel("number of neurons in 1st hidden layer")
plt.ylabel("cross-validate recall")
plt.show()