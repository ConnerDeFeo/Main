from flask_restful import Resource

from flask_restful import request
from flask_restful import reqparse
import json
from .swen_344_db_utils import *

def getArgs(args):
    parser = reqparse.RequestParser()
    for e in args:
        parser.add_argument(e,type=str)
    return parser.parse_args()

def getClubs():
    clubs=exec_get_all("SELECT * FROM clubs ORDER BY ID ASC")
    clubsJson=[]
    for club in clubs:
        clubsJson.append({'id':club[0],'name':club[1],'location':club[2],'genre':club[3],'yellowThreshold':club[4],'maxCap':club[5],'counter':club[6],'isHidden':club[7]})
    return clubsJson

class ClubAPI(Resource):
    def get(self):
        return getClubs()
    def put(self):
        args=getArgs(['name','location','genre','yellowThreshold','maxCap','counter','id'])
        exec_commit("""UPDATE clubs SET name=%s,location=%s,genre=%s,yellowThreshold=%s,maxCap=%s,counter=%s where id=%s;""",
        (args['name'],args['location'],args['genre'],args['yellowThreshold'],args['maxCap'],args['counter'],args['id']))
        return getClubs()
    def post(self):
        args=getArgs(['name','location','genre','yellowThreshold','maxCap'])
        exec_commit("""INSERT INTO clubs (name,location,genre,yellowThreshold,maxCap) 
        VALUES(%s,%s,%s,%s,%s); UPDATE CLUBS SET isHidden=FALSE""",(args['name'],args['location'],args['genre'],args['yellowThreshold'],args['maxCap']))
        return getClubs()
    def delete(self):
        id=request.args.get('id')
        exec_commit("DELETE FROM clubs WHERE id=%s",(id,))
        return getClubs()
    
class CounterAPI(Resource):
    def put(self):
        args=getArgs(['id','number'])
        if int(args['number'])==1:
            exec_commit("UPDATE clubs SET counter=counter+1 WHERE id=%s AND counter<maxCap",(args['id'],))
        else:
            exec_commit("UPDATE clubs SET counter=counter-1 WHERE id=%s AND counter>0",(args['id'],))
        return getClubs()
    
class FilterAPI(Resource):
    def put(self):
        location=request.args.get('location')
        if(location==""):
            exec_commit("UPDATE clubs SET isHidden=FALSE")
        else:
            exec_commit("""UPDATE clubs SET isHidden=CASE WHEN location != %s THEN TRUE ELSE FALSE END""",(location,))
        return getClubs()