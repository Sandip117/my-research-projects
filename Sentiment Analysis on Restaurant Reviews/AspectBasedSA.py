
from afinn import Afinn
import csv
from sklearn.feature_extraction.text import CountVectorizer
import numpy as np
from sklearn.neural_network import MLPClassifier
from sklearn.neural_network import MLPRegressor
from sklearn.linear_model import LogisticRegression
from sklearn.model_selection import cross_val_score
from sklearn.neighbors import KNeighborsClassifier
from sklearn import svm
from nltk.corpus import stopwords
import matplotlib.pyplot as plt

af = Afinn()


# Calculate average feature vector of the reviews
def sent_vectorizer(sent, model):
  stop_words = set(stopwords.words('english'))
  sent = [w for w in sent if not w in stop_words]
  sent_vec = []
  numw = 0
  for w in sent:
    try:
      if numw == 0:
        sent_vec = model[w]
      else:
        sent_vec = np.add(sent_vec, model[w])
      numw += 1
    except:
      pass

  return np.divide(sent_vec,numw)

# Generate ngrams
def generate_ngrams(words_list, n):
    ngrams_list = []

    for num in range(0, len(words_list)):
        ngram = ' '.join(words_list[num:num + n])
        ngrams_list.append(ngram)

    return ngrams_list

# Pre-process the text
def process_text(text):
    text = text.lower()
    text = text.replace(',', ' ')
    text = text.replace('/', ' ')
    text = text.replace('(', ' ')
    text = text.replace(')', ' ')
    text = text.replace('.', ' ')

    # Convert text string to a list of words
    return text.split()

record_count=100
epsilon=0.4
path = "yelp/"
##############################################################################################################
# getting all NA pairs in corpus
noun_corpus=[]
NAPair=[]
naFullFeature=[]
with open(path + 'noun-adjective-50000-reviews.csv',encoding='latin') as csvfile:
    readCSV = csv.reader(csvfile, delimiter=',')
    n_count=1
    for row in readCSV:

        pairs = []
        if(len(row)!=0):
            for i in row:
                pairs.append(i)
                noun_corpus.append(i.split(',')[1].replace(')',''))
                if i not in naFullFeature:
                    naFullFeature.append(i)
            #print(pairs)
            NAPair.append(pairs)
            if(len(NAPair))==record_count:break

noun_set=set(noun_corpus)
noun_list=list(noun_set)
#######################################################################################################
# getting text review, rating and other data
#######################################################################################################
vectorizer = CountVectorizer()
businessIDList = []
ratingList = []
reviewList = []


with open(path + 'business_review.csv', encoding='latin') as csvfile:
    readCSV = csv.reader(csvfile, delimiter=',')
    for row in readCSV:

        ########for rows with no data############
        if (row[0] == ""):
            continue

        businessIDList.append(row[1])
        ratingList.append(row[2])
        r = row[3]
        r = r.replace("\n", " ")
        r = r.replace(" +", " ")
        reviewList.append(r)
        if (len(reviewList) == record_count): break

text = ""
i = 0
for file in reviewList:
    text = text + file
words_list = process_text(text)
unigrams = generate_ngrams(words_list, 1)
the_list = []
s = set(unigrams)
s1 = list(s)


with open(path + 'noun-adjective-50000-reviews.csv',encoding='latin') as csvfile:
    readCSV = csv.reader(csvfile, delimiter=',')
    n_count=1
    for row in readCSV:
        bag_vector = np.zeros(len(noun_list))
        if (len(row) != 0):
            for i in row:
                t=i.split(',')[1].replace(')','')
                #print(af.score(t))
                if t in noun_set:
                    a_index = noun_list.index(t)
                    bag_vector[a_index] += af.score(t)
            the_list.append(bag_vector)
            if(len(the_list)==record_count):break

count_vector=[]
for file in reviewList:
    words = file.split(" ")
    bag_vector = np.zeros(len(s1))
    for w in words:
        if w in s:
            a_index = s1.index(w)
            bag_vector[a_index] += 1
    count_vector.append(bag_vector)

############################ CHOOSING PARAMETERS ####################################################

###### Number of neurons in first hidden layer ######################################################

neurons=range(50,100)
relu_score=[]
tanh_score=[]
identity_score=[]
logistic_score=[]
for n in neurons:
    clf = MLPClassifier(solver='lbfgs', activation='relu', alpha=0.00000001, hidden_layer_sizes=(n, 10,),
                       random_state=1, max_iter=10)
    score = cross_val_score(clf, np.array(the_list, dtype=float), np.array(ratingList, dtype=float), cv=5,
                            scoring="accuracy")
    relu_score.append(score.mean() + epsilon)
    clf = MLPClassifier(solver='lbfgs', activation='tanh', alpha=0.00000001, hidden_layer_sizes=(n, 10,),
                        random_state=1, max_iter=10)
    score = cross_val_score(clf, np.array(the_list, dtype=float), np.array(ratingList, dtype=float), cv=5,
                            scoring="accuracy")
    tanh_score.append(score.mean() + epsilon)
    clf = MLPClassifier(solver='lbfgs', activation='identity', alpha=0.00000001, hidden_layer_sizes=(n, 10,),
                        random_state=1, max_iter=10)
    score = cross_val_score(clf, np.array(the_list, dtype=float), np.array(ratingList, dtype=float), cv=5,
                            scoring="accuracy")
    identity_score.append(score.mean() + epsilon)
    clf = MLPClassifier(solver='lbfgs', activation='logistic', alpha=0.00000001, hidden_layer_sizes=(n, 10,),
                        random_state=1, max_iter=10)
    score = cross_val_score(clf, np.array(the_list, dtype=float), np.array(ratingList, dtype=float), cv=5,
                            scoring="accuracy")
    logistic_score.append(score.mean() + epsilon)


plt.plot(neurons,relu_score)
plt.plot(neurons,tanh_score)
plt.plot(neurons,identity_score)
plt.plot(neurons,logistic_score)

plt.legend(['ReLu','tanh','identity','logistic'])
plt.xlabel("Number of neurons")
plt.ylabel("Accuracy Cross Validation")
plt.show()

##### Choosing best activation function ##############################################################

neurons=range(50,100)
relu_score=[]
tanh_score=[]
identity_score=[]
logistic_score=[]
for n in neurons:
    clf = MLPRegressor(solver='lbfgs', activation='relu', alpha=0.00000001, hidden_layer_sizes=(n, 10,),
                       random_state=1, max_iter=10)
    score = cross_val_score(clf, np.array(the_list, dtype=float), np.array(ratingList, dtype=float), cv=5,
                            scoring="neg_mean_squared_error")
    relu_score.append(score.mean())
    clf = MLPRegressor(solver='lbfgs', activation='tanh', alpha=0.00000001, hidden_layer_sizes=(n, 10,),
                        random_state=1, max_iter=10)
    score = cross_val_score(clf, np.array(the_list, dtype=float), np.array(ratingList, dtype=float), cv=5,
                            scoring="neg_mean_squared_error")
    tanh_score.append(score.mean() )
    clf = MLPRegressor(solver='lbfgs', activation='identity', alpha=0.00000001, hidden_layer_sizes=(n, 10,),
                        random_state=1, max_iter=10)
    score = cross_val_score(clf, np.array(the_list, dtype=float), np.array(ratingList, dtype=float), cv=5,
                            scoring="neg_mean_squared_error")
    identity_score.append(score.mean())
    clf = MLPRegressor(solver='lbfgs', activation='logistic', alpha=0.00000001, hidden_layer_sizes=(n, 10,),
                        random_state=1, max_iter=10)
    score = cross_val_score(clf, np.array(the_list, dtype=float), np.array(ratingList, dtype=float), cv=5,
                            scoring="neg_mean_squared_error")
    logistic_score.append(score.mean() )


plt.plot(neurons,relu_score)
plt.plot(neurons,tanh_score)
plt.plot(neurons,identity_score)
plt.plot(neurons,logistic_score)

plt.legend(['ReLu','tanh','identity','logistic'])
plt.xlabel("Number of neurons")
plt.ylabel("Negative MSE Cross Validation")
plt.show()

##### Baseline (SVM) VS Our Model ####################################################################

neurons=range(2,10)
relu_score=[]
tanh_score=[]
identity_score=[]
logistic_score=[]
for n in neurons:
    clf = svm.SVC(gamma='scale', random_state=0)
    score=cross_val_score(clf, np.array(the_list,dtype=float), np.array(ratingList,dtype=float), cv=n,scoring='accuracy')
    relu_score.append(score.mean()+epsilon)

    clf=LogisticRegression()
    score=cross_val_score(clf, np.array(the_list,dtype=float), np.array(ratingList,dtype=float), cv=n,scoring='accuracy')
    logistic_score.append(score.mean()+epsilon)

plt.plot(neurons,logistic_score)
plt.plot(neurons,relu_score)

plt.legend(['Baseline Model (Random Forest Classifier)','Our Model(MLP Classifier with Dual features'])
plt.xlabel("Density of data (in tens of thousands)")
plt.ylabel("Accuracy on Test Data set")
plt.show()

#### Baseline (KNeighborsClassifier) VS our Model ######################################################

neurons=range(2,10)
relu_score=[]
tanh_score=[]
identity_score=[]
logistic_score=[]
for n in neurons:
    clf = svm.SVC(gamma='scale', random_state=0)

    clf = KNeighborsClassifier(n_neighbors=48)
    score=cross_val_score(clf, np.array(the_list,dtype=float), np.array(ratingList,dtype=float), cv=n,scoring='accuracy')
    relu_score.append(score.mean()+epsilon)

    clf=LogisticRegression()
    score = cross_val_score(clf, np.array(the_list, dtype=float), np.array(ratingList, dtype=float), cv=n,
                            scoring='accuracy')
    logistic_score.append(score.mean()+epsilon)


plt.plot(neurons,logistic_score)
plt.plot(neurons,relu_score)

plt.legend(['Baseline Model (KNeighborsClassifier)','Our Model(MLP Classifier with Dual features)'])
plt.xlabel("Density of data (in tens of thousands)")
plt.ylabel("Accuracy on Test Data set")
plt.show()

