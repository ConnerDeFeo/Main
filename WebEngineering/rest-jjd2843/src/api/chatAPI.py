from flask_restful import Resource
from flask_restful import request
from flask_restful import reqparse
import json

from db.chatDB import *

def getArgs(args):
    parser = reqparse.RequestParser()
    for e in args:
        parser.add_argument(e,type=str)
    return parser.parse_args()

class Login(Resource):
    def put(self):
        args=getArgs(['username','password'])
        session_key=login(args['username'],args['password'])

        if session_key is not None:
            return session_key
        return "Incorrect Username or Password"
    
    def delete(self):
        username=request.args.get('username')
        session_key=request.args.get('session_key')
        return logout(username,session_key)

class Users(Resource):
    def get(self):
        return list_users()
    def post(self):
        args = getArgs(['username','name','phone_number','date_created','password'])

        username=args['username']
        name=args['name']
        phone_number=args['phone_number']
        date_created=args['date_created']
        password=args['password']

        if date_created is not None:
            if create_user(username,name,phone_number,password,date_created):
                return True
            return False
        else:
            if create_user(username,name,phone_number,password):
                return True
            return False

    def put(self):
        args=getArgs(['username','session_key','new_name','new_phone_number'])

        username=args['username']
        session_key=args['session_key']
        new_name=args['new_name']
        new_phone_number=args['new_phone_number']

        valid_username=False

        if new_phone_number is not None:
            if update_phone_number(username,session_key,new_phone_number):
                valid_username=True
        if new_name is not None:
            if update_name(username,session_key,new_name):
                valid_username=False
            
        return valid_username

    def delete(self):
        return delete_user(request.args.get('username'),request.args.get('session_key'))
        
class Messages(Resource):
    def get(self):
        word=request.args.get('word')
        start_date=request.args.get('start')
        end_date=request.args.get('end')
        return full_text_search(word,start=start_date,end=end_date)
    
class DirectMessages(Resource):
    def get(self):
        username=request.args.get('username')
        session_key=request.args.get('session_key')
        sender=request.args.get('sender')
        start=request.args.get('start')
        end=request.args.get('end')
        number=request.args.get('number')
        asc=request.args.get('asc')

        if asc is not None:
            return get_messages(username,session_key,sender,start,end,number,asc)
        else:
            return get_messages(username,session_key,sender,start,end,number)
        
    def post(self):
        username=request.args.get('username')
        receiver=request.args.get('receiver')
        session_key=request.args.get('session_key')
        message=request.args.get('message')
        send_direct_message(username,session_key,receiver,message)
    
class Communities(Resource):
    def get(self):
        return list_communities_channel()

class Channels(Resource):
    def get(self,id):
        return list_channel_messages(id)
