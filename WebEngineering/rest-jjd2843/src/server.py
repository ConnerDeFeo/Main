from flask import Flask
from flask_restful import Api
from api.management import *
from api.chatAPI import *

app = Flask(__name__)
api = Api(app)

api.add_resource(Init, '/manage/init') #Management API for initializing the DB
api.add_resource(Version, '/manage/version') #Management API for checking DB version
api.add_resource(Login,'/login')
api.add_resource(Users, '/users')
api.add_resource(Communities,'/communities')
api.add_resource(Messages,'/messages')
api.add_resource(DirectMessages,'/directMessages')
api.add_resource(Channels,'/channel/<int:id>')

if __name__ == '__main__':
    make_tables()
    app.run(debug=True)