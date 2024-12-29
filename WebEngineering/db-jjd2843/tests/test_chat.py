import unittest
from src.chat import *
from src.swen344_db_utils import exec_sql_file
import time


#seed tables
def seed_data():
    make_tables()
    exec_sql_file('db-jjd2843/tests/seedtest.sql')


class TestChat(unittest.TestCase):
    def test_get_messages_Abbott_Costello(self):
        seed_data()
        c=0
        for e in get_messages('Abbott','Costello'):
            c+=1
        self.assertEqual(c,5)
    #shown messages in 1935 not 1995
   
    def test_get_messages_1995_Moe_Larry(self):
        seed_data()
        c=0
        for e in get_messages('Moe','Larry','1935-01-01 00:00:00','1936-01-01 00:00:00'):
            c+=1
        self.assertEqual(c,2)


    def test_unread_to_Abbot(self):
        seed_data()
        self.assertEqual(get_unread_count_direct_messages('Abbott'),2)


    def test_check_is_suspended(self):
        seed_data()
        self.assertTrue(is_suspended('Larry','2012-05-04 00:00:00'))
        self.assertTrue(is_suspended('Larry'))


    def test_check_is_not_suspended(self):
        seed_data()
        self.assertFalse(is_suspended('Curly','2000-02-29 00:00:00'))
        self.assertFalse(is_suspended('Curly'))


    def test_unsuspend_user(self):
        seed_data()
        unsuspend_user('Larry')
        time.sleep(0.5)
        self.assertFalse(is_suspended('Larry'))


    def test_change_username(self):
        seed_data()
        self.assertTrue(update_username('Larry','Jerry'))
        self.assertFalse(update_username('Jerry','Larry'))
        self.assertTrue(update_username('Curly','Burly'))


    def test_create_user(self):
        seed_data()
        create_user('Bob','0987','1961-05-17 09:00:00')
        self.assertEqual(get_user_id('Bob'),9)


    def test_send_message(self):
        seed_data()
        create_user('Bob','0987','1961-05-17 09:00:00')
        self.assertEqual(len(get_messages('Bob','DrMarvin')),0)
        send_direct_message('Bob','DrMarvin',"Im doing the work, Im baby-stepping",'1961-05-18 09:00:00')
        self.assertEqual(get_messages('Bob','DrMarvin')[0][0],'Im doing the work, Im baby-stepping')
        self.assertEqual(str(exec_get_all("SELECT time_sent FROM direct_messages WHERE receiver_id=6")[0][0]),'1961-05-18 09:00:00')
        self.assertFalse(exec_get_one("SELECT is_read FROM direct_messages WHERE receiver_id=6")[0])


    def test_change_username(self):
        seed_data()
        create_user('Bob','0987','1961-05-17 09:00:00')
        self.assertTrue(update_username("Bob","BabySteps2Door",'1961-05-19 09:00:00'))
        self.assertFalse(update_username('Bob','BabySteps2Elevator','1961-05-20 09:00:00'))


    def test_list_and_unread_messages(self):
        seed_data()
        create_user('Bob','0987','1961-05-17 09:00:00')
        send_direct_message('Bob','DrMarvin',"Im doing the work, Im baby-stepping",'1961-05-18 09:00:00')
        list,unread_count=list_of_users("DrMarvin")
        self.assertEqual(unread_count,1)
        self.assertEqual(list[0][0],'Bob')
        read_direct_messages('DrMarvin','Bob')
        self.assertTrue(exec_get_one("SELECT is_read FROM direct_messages WHERE receiver_id=6")[0],True)


    def test_csv_parsing(self):
        seed_data()
        collect_csv_messages('data/whos_on_first.csv')
        self.assertEqual(exec_get_one('SELECT text_sent FROM direct_messages WHERE message_id=216')[0],'(Fumbles words loudly)')
        self.assertEqual(exec_get_one('SELECT text_sent FROM direct_messages WHERE message_id=164')[0],'Third base!')
        self.assertEqual(exec_get_one('SELECT text_sent FROM direct_messages WHERE message_id=165')[0],'Third base!')


    def test_join_community(self):
        seed_data()
        join_community('Paul','Arrakis')
        self.assertEqual(exec_get_one("SELECT user_id FROM users_communities WHERE user_id=8")[0],8)


    def test_send_channel_message(self):
        seed_data()
        send_channel_message('Paul','Arrakis','#Worms','UNDER THE BLUE SEA OR SOMETHING')
        self.assertEqual(get_unread_count_channel('spicelover','Arrakis','#Worms'),1)


    def test_get_mentioned_messages(self):
        seed_data()
        send_channel_message('Paul','Arrakis','#Worms',"Yo my boy, @spicelover wya")
        self.assertEqual(len(get_list_of_mentions('spicelover')),1)
        send_channel_message('Moe','Comedy','#Dialogs',"Im in love with @spicelover")


    def test_suspend_user_community(self):
        seed_data()
        suspend_user('Paul','2225-01-01 00:00:00',community_name='Arrakis')
        self.assertFalse(send_channel_message('Paul','Arrakis','#Worms','Domain expansion: I like sand'))


    def test_suspend_user_community(self):
        seed_data()
        suspend_user('Paul','2225-01-01 00:00:00',community_name='Arrakis')
        self.assertFalse(send_channel_message('Paul','Arrakis','#Worms','I am banned'))
        self.assertFalse(send_channel_message('Paul','Arrakis','#Random','I am banned'))
        self.assertTrue(send_channel_message('Paul','Comedy','#Dialogs','I am NOT banned here'))
        self.assertTrue(send_channel_message('Paul','Comedy','#ArgumentClinic','I am NOT banned here'))


    def test_dms_paul_moe(self):
        seed_data()
        self.assertEqual(len(get_messages('Paul','Moe')),6)

    def test_full_text_search(self):
        seed_data()
        print('Full text search:')
        print('----------------------------------------------------')

        print(full_text_search('Reply'))
        self.assertEqual(full_text_search('Reply')[0],'Please reply!')
        self.assertEqual(full_text_search('Reply')[1],'I replied already!')
        self.assertEqual(full_text_search('Reply')[2],'Please reply!')
        self.assertEqual(full_text_search('Reply')[3],'I replied already!')

        print(full_text_search('reply please'))
        self.assertEqual(full_text_search('reply please')[0],'Please reply!')
        self.assertEqual(full_text_search('reply please')[1],'Please reply!')
        self.assertEqual(len(full_text_search('reply please')),2)

        print("Only channel messages: "+str(full_text_search('Reply',channel_messages=True)))
        print("Only Direct messages: "+str(full_text_search('Reply',direct_messages=True)))
        self.assertEqual(full_text_search('Reply',direct_messages=True)[0],'Please reply!')
        self.assertEqual(full_text_search('Reply',direct_messages=True)[1],'I replied already!')
        self.assertEqual(full_text_search('Reply',channel_messages=True)[0],'Please reply!')
        self.assertEqual(full_text_search('Reply',channel_messages=True)[1],'I replied already!')
        print()

    def test_get_active_summary(self):
        seed_data()
        print('Active Summary:')
        print('----------------------------------------------------')

        print(active_summary('2024-09-29 00:00:00'))
        self.assertEqual(float(active_summary('2024-09-29 00:00:00')[0][1]),float((11.0/30.0)))
        self.assertEqual(float(active_summary('2024-09-29 00:00:00')[1][1]),float((0.4)))
        self.assertEqual(active_summary('2024-09-29 00:00:00')[0][0],'Arrakis')
        self.assertEqual(active_summary('2024-09-29 00:00:00')[1][0],'Comedy')
        self.assertEqual(active_summary('2024-09-29 00:00:00')[0][2],8)
        self.assertEqual(active_summary('2024-09-29 00:00:00')[1][2],8)
        print()

    def test_get_suspended_users_messages(self):
        seed_data()
        print("Unsuspended User Messages")
        print('----------------------------------------------------')

        suspend_user('Paul','2060-01-01 00:00:00','2000-01-01 00:00:00','Arrakis')
        print("As of 2024-09-28: "+str(watch_suspended_users('2024-09-28 00:00:00')))
        self.assertEqual(len(watch_suspended_users('2024-09-28 00:00:00')),1)
        self.assertEqual(watch_suspended_users('2024-09-28 00:00:00')[0],'Paul')

        print("As of 1995-01-01: "+str(watch_suspended_users('1995-01-01 00:00:00')))
        self.assertEqual(len(watch_suspended_users('1995-01-01 00:00:00')),1)
        self.assertEqual(watch_suspended_users('1995-01-01 00:00:00')[0],'Abbott')
        self.assertEqual(len(watch_suspended_users('1995-01-01 00:00:00','1994-12-30 00:00:00')),0)
        print()

if __name__ == '__main__':
    unittest.main()
       

    