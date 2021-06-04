import json

from rest_framework.views import APIView
from rest_framework.response import Response
import textsimilarities.text_similarity as ts


class TextAnalyzeView(APIView):
    def get(self, request):
        return Response({"hello"})

    def post(self, request):
        body_unicode = request.body.decode('utf-8')
        body = json.loads(body_unicode)
        answer = body['answer']
        content = body['correct']
        content.append(answer)
        matrix = ts.vectorize_texts(content)
        for matr in matrix[len(matrix)-1]:
            print(str(matr))
        matrix[len(matrix) - 1][len(matrix) - 1] = 0.0
        return Response(max(matrix[len(matrix)-1]))

