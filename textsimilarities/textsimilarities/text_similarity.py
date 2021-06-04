import string
import urllib.request  # библиотека для скачивания данных

import gensim
import gensim
import gensim
import gensim.downloader as api
import nltk
import numpy as np
import pandas as pd
import pymystem3 as pymystem3
from gensim import corpora
from gensim import corpora
from gensim.matutils import softcossim
from gensim.matutils import softcossim
from gensim.models import word2vec
from gensim.models.tfidfmodel import TfidfModel
from gensim.utils import simple_preprocess
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize  # готовый токенизатор библиотеки nltk
from pymorphy2 import MorphAnalyzer
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.metrics.pairwise import cosine_similarity


def vectorize_texts(texts):
    nltk.download('punkt')
    nltk.download('stopwords')
    noise = stopwords.words('russian')
    tfidf_vectorizer = TfidfVectorizer(stop_words=noise)

    return soft_cosine_similarity_matrix(count_vectorize(texts))

def cosine_sim(df):
    return cosine_similarity(df, df)

def soft_cosine_similarity_matrix(texts):

    dictionary = corpora.Dictionary([simple_preprocess(doc) for doc in texts])
    model_path = 'ruscorpora_mystem_cbow_300_2_2015.bin.gz'
    fasttext_model300 = gensim.models.KeyedVectors.load_word2vec_format(model_path, binary=True)
    similarity_matrix = fasttext_model300.similarity_matrix(dictionary, tfidf=None, threshold=0.0, exponent=2.0,
                                                            nonzero_limit=100)
    sent = []
    for text in texts:
        sent.append(dictionary.doc2bow(simple_preprocess(text)))
    len_array = np.arange(len(sent))
    xx, yy = np.meshgrid(len_array, len_array)
    cossim_mat = pd.DataFrame([[round(softcossim(sent[i], sent[j], similarity_matrix) ,2) for i, j in zip(x,y)] for y, x in zip(xx, yy)])
    return cossim_mat
#
def lemmatize(sentence):
    pymrth_analyzer = MorphAnalyzer()
    new_sentence = []

    for ch in string.punctuation:
        sentence = sentence.replace(ch,"")

    sent = word_tokenize(sentence)
    normal_sent = ""

    for i in range(len(sent)):
        word = pymrth_analyzer.parse(sent[i])
        normal_word = word[0].normal_form
        normal_sent += (normal_word + " ")

    new_sentence.append(normal_sent)

    return new_sentence

def count_vectorize(texts):
    noise = stopwords.words('russian')

    new_docs = []

    for i in range(len(texts)):
        sent = lemmatize(texts[i])
        new_docs.append(sent[0])

    print(new_docs)

    documents = new_docs
    return documents
