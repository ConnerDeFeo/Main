import unittest
from tests.test_utils import *
import json

HDR={'content-type': 'application/json'}

def seed_data(self):
    """seeds data_base
        
        used by tests
        
        returns nothing"""
    post_rest_call(self, 'http://localhost:5000/manage/init')

def print_users(self):
    """Prints all current users in neat format
    
        used by tests for cleanliness
        
        returns list of users"""
    users=get_rest_call(self,'http://localhost:5000/users')
    print("")
    for e in users:
        print(e[0]+", "+e[1])
    print('')
    return users

def login(self,name,pword):
    """Logs user in
    
        username(str): username of user
        password(str): passwor of user
        
        used internally by tests for cleanliness
        
        returns either session key or none"""
    
    return put_rest_call(self, 'http://localhost:5000/login', json.dumps(dict(username=name, password=pword)), HDR)

def mock_user(self,username,name,phone,pword):
    """Creates user and loggs them in
    
        used by tests, im lazy na ddon't want to constantly re-type this to get a user with a proper hash password
        
        returns session kery for user logged in"""
    
    data = dict(username=username,name=name, phone_number=phone,password=pword)
    jdata = json.dumps(data)
    post_rest_call(self, 'http://localhost:5000/users', jdata, HDR)
    return login(self,username,pword)

"""Individual testing(I don't want to re-type it): python -m unittest tests.api.test_chatAPI.TestChatAPI."""

class TestChatAPI(unittest.TestCase):

    def test_list_users(self):
        seed_data(self)
        expected=['Abbott','123       ']
        actual=get_rest_call(self,'http://localhost:5000/users')
        self.assertEqual(expected, actual[0])
        self.assertEqual(8,len(actual))

    def test_list_communities_channels(self):
        seed_data(self)
        actual=get_rest_call(self,'http://localhost:5000/communities')
        self.assertEqual(len(actual),2)
        expected1=[1, 'Arrakis', ['#Worms', '#Random']]
        expected2=[2, 'Comedy', ['#ArgumentClinic', '#Dialogs']]
        self.assertEqual(expected1,actual[0])
        self.assertEqual(expected2,actual[1])

    def test_list_channel_messages(self):
        seed_data(self)
        actual=get_rest_call(self,"http://localhost:5000/channel/1")
        self.assertEqual(8,len(actual))

    def test_list_channel_messages_does_not_exist(self):
        seed_data(self)
        actual=get_rest_call(self,"http://localhost:5000/channel/5")
        self.assertEqual(0,len(actual))

    def test_full_text_search(self):
        seed_data(self)
        actual=get_rest_call(self,'http://localhost:5000/messages?word=Reply')
        actual2=get_rest_call(self,'http://localhost:5000/messages?word=Reply&start=2024-09-22&end=2024-09-22')
        actual3=get_rest_call(self,'http://localhost:5000/messages?word=Reply&end=2024-09-21')
        self.assertEqual(4,len(actual))
        self.assertEqual(1,len(actual2))
        self.assertEqual(3,len(actual3))

    def test_create_user(self):
        seed_data(self)
        print('(Create User) \nBefore: ')
        print_users(self)
        jdata = json.dumps(dict(username='jjd2843',name='Jack', phone_number='3158797067',password='Saturo Gojo'))
        post_rest_call(self, 'http://localhost:5000/users', jdata, HDR)
        print('After:')
        self.assertEqual(['Jack','3158797067'],print_users(self)[4])
        print('Attempting to add second user with the same data:')
        post_rest_call(self,'http://localhost:5000/users', jdata, HDR)
        self.assertEqual(9,len(print_users(self)))

    def test_login(self):
        seed_data(self)
        print('(Login)')
        print('Creating and logging in user...')
        session_key=mock_user(self,'jjd2843','Jack','1234567890','Saturo Gojo')
        self.assertIsNotNone(session_key)
        print('Users Session Key = '+str(session_key))
        print()
        print('Username does not exist:')
        no_username=login(self,'w','Saturo Gojo')
        self.assertEqual("Incorrect Username or Password",no_username)
        print("Should be error message: "+str(no_username))
        print()
        print('Incorrect Password')
        wrong_password=login(self,'Jack','Gojo')
        self.assertEqual("Incorrect Username or Password",wrong_password)
        print("Should be error message: "+str(wrong_password))
        print()

    def test_logout(self):
        seed_data(self)
        print('(Logout)\n creating and logging in Jack...')
        session_key=mock_user(self,'jjd2843','Jack','4567890','Gojo')
        logout=delete_rest_call(self,'http://localhost:5000/login?username=jjd2843&session_key='+session_key)
        print('Should be True: '+str(logout)+'\n\nAttempting to logout Jack twice...\n')
        self.assertTrue(logout)
        logout=logout=delete_rest_call(self,'http://localhost:5000/login?username=jjd2843&session_key='+session_key)
        print('Should be False: '+str(logout))
        self.assertFalse(logout)

    def test_update_name(self):
        seed_data(self)
        print("(Update Username)\nBefore:")
        print_users(self)
        print('Updating Abbotts username to Jackery...')
        put_rest_call(self, 'http://localhost:5000/users', json.dumps(dict(username="ab12",session_key='x', new_name='Jackery')), HDR)
        print('Checking if Jackery exists and Abbot does not: ')
        users=print_users(self)
        self.assertEqual(['Jackery','123       '],users[3])
        self.assertNotEqual(['Abbott','123       '],users[0])
        print('Attempting to update user with improper username...')
        print('Should have be False: '+str(put_rest_call(self, 'http://localhost:5000/users', json.dumps(dict(username="ct12",session_key='x', new_username='Jackery')), HDR)))
        print('Checking if any users have been changed:')
        print_users(self)

    def test_update_phone_number(self):
        seed_data(self)
        print('(Update Phone Number)\nBefore:')
        print_users(self)
        print('Changing phone-number for Abbott...')
        put_rest_call(self, 'http://localhost:5000/users', json.dumps(dict(username="ab12",session_key='x', new_phone_number='1234567890')), HDR)
        print('Checking if phone-number has been changed for Abbot')
        self.assertEqual(['Abbott','1234567890'],print_users(self)[0])
        print('Attempting to update user that does not exist...')
        print('Should have be False: '+str(put_rest_call(self, 'http://localhost:5000/users', json.dumps(dict(username="ct12",session_key='x', new_phone_number='1462890938')), HDR)))
        print('Checking if any users have been changed:')
        print_users(self)

    def test_update_both(self):
        seed_data(self)
        print('(Update Username and Phone Number)\nBefore:')
        print_users(self)
        print('Changing Abbotts username to Jackery and phonenumer to 1234567890...')
        put_rest_call(self, 'http://localhost:5000/users', json.dumps(dict(username="ab12",session_key='x',new_name='Jackery',new_phone_number='1234567890')), HDR)
        print('Abbott shoud be changed:')
        users=print_users(self)
        self.assertNotEqual(['Abbott','123       '],users[0])
        self.assertEqual(['Jackery','1234567890'],users[3])
        print('Attempting to change invalid username...')
        updated_false=put_rest_call(self, 'http://localhost:5000/users', json.dumps(dict(username='ct12',session_key='x',new_username='Jackery',new_phone_number='1234567890')), HDR)
        print('Should be False: '+str(updated_false))
        print('No users should have changed:')
        print_users(self)
        self.assertFalse(updated_false)

    def test_delete_user(self):
        seed_data(self)
        print('(Delete User)\nBefore:')
        print_users(self)
        print('Deleting Abbott...')
        deletedT=delete_rest_call(self, 'http://localhost:5000/users?username=ab12&session_key=x')
        print('Abbott should be wiped from existance: ')
        self.assertNotEqual(['Abbott','123       '],print_users(self)[0])
        self.assertTrue(deletedT)
        print('Attempting to delete Abbott again because I hate him(Using different Costellos Key): ')
        deletedF=delete_rest_call(self, 'http://localhost:5000/users?username=ab12&session_key=y')
        print('Should be false: '+str(deletedF))
        self.assertFalse(deletedF)
        print('No users should have been deleted: ')
        self.assertEqual(7,len(print_users(self)))

    def test_get_DMs(self):
        seed_data(self)
        print('(List DMs)\nAll of Abbotts DMs: \n')
        c=0
        all_messages=get_rest_call(self,'http://localhost:5000/directMessages?username=ab12&session_key=x')
        for e in all_messages:
            print(e)
            c+=1
        print('Total: '+str(c)+"\n\nShould only show 5 messages: \n")
        five=get_rest_call(self,'http://localhost:5000/directMessages?username=ab12&session_key=x&number=5')
        c2=0
        for e in five:
            print(e)
            c2+=1
        print('Total: '+str(c2))
        self.assertEqual(5,len(five))

    def test_send_DM(self):
        seed_data(self)
        abbott_original_number=len(get_rest_call(self,'http://localhost:5000/directMessages?username=ab12&session_key=x'))
        costello_original_number=len(get_rest_call(self,'http://localhost:5000/directMessages?username=ct12&session_key=y'))
        print('(Send DM)\nNumber DMS Abbott: '+str(abbott_original_number)+'\nNumber of DMs Costello: '+str(costello_original_number)
        +'\n\nAbbott sending message to Costello...\n')
        self.assertEqual(11,abbott_original_number)
        self.assertEqual(9,costello_original_number)
        post_rest_call(self,'http://localhost:5000/directMessages?username=ab12&session_key=x&receiver=ct12&message=testing')
        abbott_new_number=len(get_rest_call(self,'http://localhost:5000/directMessages?username=ab12&session_key=x'))
        costello_new_number=len(get_rest_call(self,'http://localhost:5000/directMessages?username=ct12&session_key=y'))
        print('Abbott new number of DMs: '+str(abbott_new_number))
        print('Costello new number of DMs: '+str(costello_new_number))
        self.assertEqual(12,abbott_new_number)
        self.assertEqual(10,costello_new_number)
