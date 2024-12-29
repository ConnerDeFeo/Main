from flask_restful import Resource, reqparse, request  #NOTE: Import from flask_restful, not python

from db.swen344_db_utils import *

from db.chatDB import make_tables

class Init(Resource):
    def post(self):
        make_tables()
        exec_sql_file('src/db/seed_test.sql')
class Version(Resource):
    def get(self):
        return (exec_get_one('SELECT VERSION()'))