from db.swen344_db_utils import *
import hashlib
import secrets

def make_tables():
    """Base function for creating tables
        
        should only be used when initially creating the data basse or for testing
        
        returns nothing"""

    exec_commit("""DROP TABLE IF EXISTS suspensions, users, direct_messages, communities, channels, users_communities, channel_messages,users_channels CASCADE""")
    exec_sql_file('src/db/main.sql')

def check_session_key(username,session_key):
    """Checks to see if session key is active for a given user
    
        username(str): username for user
        session_key(str): session key being checked
        
        should be used internally by server or db
        
        returns True if it's valid, False esle"""
    
    uid=exec_get_one("SELECT user_id FROM users WHERE username=%s AND session_key=%s",(username,session_key))

    if uid is not None:
        return uid[0]
    return uid

def get_user_id(username):
    """Gets a single users id
    
        usernsame (str): username of the User who's id is returned
        
        used only internally within other functions
        
        returns user_id"""
    uid=exec_get_one("SELECT user_id FROM users WHERE username = %s",(username,))
    if uid is not None:
        return uid[0]
    return uid

def get_community_id(community_name):

    """Returns the community_id for a given community name

        community_name(str): name of new community being made

        Internal function to retrieve community ID based on name

        Returns the community_id"""
    
    return exec_get_one("SELECT community_id FROM communities WHERE community_name = %s",(community_name,))[0]

def get_channel_id(com_id,channel_name):
    """Find channel id for given channel name in a given community
    
        com_id(int): Community_id of the community that contains the channel
        channel_name(str): name of the channel whos id will be returned
        
        this should only be used internally by other functions
        
        returns channel_id"""
    return exec_get_one("""SELECT channel_id FROM channels WHERE community_id=%s and channel_name=%s""",(com_id,channel_name))[0]

def __hashPassword(password:str):
    """Generates a secure hashkey for a password
    
        password(str): Password being converted
        
        used internally by db functions
        
        returns nothing"""
    
    sha512=hashlib.sha512()
    sha512.update(password.encode('utf-8'))
    return sha512.hexdigest()

def list_users():
    """Lists all users in the database
    
        used internally by admins or managment
        
        returns nothing"""
    
    return exec_get_all("""SELECT name,phone_number FROM USERS ORDER BY name ASC""")

def list_communities_channel():
    """Lists all data regarding communites, including their channels
    
        Can be used internall, by managment, or admins
        
        Returns list of lists that each conatin community name, id, and all channels in that community"""
    
    #We do not need to include channel_id, this is becasue the given channel can be retrieved using the 
    #community_id and channel name provided
    communities_channels=exec_get_all("""SELECT cm.community_id, cm.community_name, ch.channel_name
    FROM communities cm INNER JOIN channels ch ON cm.community_id=ch.community_id ORDER BY cm.community_id ASC""")

    data={}
    #Add all communites and their respective channels to a dict with id as the key value 
    for e in communities_channels:
        #If the data for that community has not been populated, do so
        if e[0] not in data:
            data[e[0]]=[e[0],e[1],[e[2]]] #channels are in a list so all sub-lists of the main on have the same amount of collums
        #else add given channel to already populated data
        else:
            data[e[0]][2].append(e[2])

    #returned as a list for cleanliness
    return list(data.values())

def list_channel_messages(channel_id):
    """Gets all messages from a given channel

        channel_id(int): id of channel
    
        Used by users who want to see all messages in a channel they are in
        
        returns list of messages in given channel"""
    
    try:
        return [e[0] for e in exec_get_all("""SELECT text_sent FROM channel_messages WHERE channel_id=%s ORDER BY time_sent ASC""",(channel_id,))]
    except:
        return []

def full_text_search(word=None,direct_messages=False,channel_messages=False,start=None,end=None):
    """Gets all messages with that apply to a given word search, message type, and/or date range
    
        word(str,optional): word or words (put in: str & str & ... format) that will be searched for. Default None
        direct_messages(boolean, optional): decides weather or not direct_messages will be searched, default false
        channel_messages(boolean, optional): decides whether or not channel_messages will be searched, default false
        start(timestamp, optional): Start date when messages will be retreived from, default none
        end(timestamp, optional): end date when messages will be retreived from, default none
        
        admins should be able to user this for data analytic purposes
        
        returns list of messages given specific filter criteria.
        If neither direct messages or channel messages is specififed, return search for both tables"""

    sql="""SELECT text_sent FROM {}"""
    clause="WHERE" #allows dynamic changes depending on scenario

    if word is not None:
        sql+=""" WHERE to_tsvector('english',text_sent) @@ to_tsquery('english',%s)"""
        #check if both or neither was called
        word="&".join(word.split())
        args=[word]
        clause="AND" #change it so that future additions do no throw error

    #check date ranges
    if start is not None and end is not None:
        sql+=clause+""" time_sent>=%s AND time_sent<=%s"""
        args.extend([start,end])
    elif start is not None:
        sql+=clause+""" time_sent>=%s"""
        args.append(start)
    elif end is not None:
        sql+=clause+""" time_sent<=%s"""
        args.append(end)

    if (not direct_messages and not channel_messages) or (direct_messages and channel_messages):
        messages_list=[e[0] for e in exec_get_all(sql.format('direct_messages'),args)]
        messages_list.extend([e[0] for e in exec_get_all(sql.format('channel_messages'),args)])
        return messages_list
    if direct_messages:
        return [e[0] for e in exec_get_all(sql.format('direct_messages'),args)]
    else:
        return [e[0] for e in exec_get_all(sql.format('channel_messages'),args)]

def create_user(username,name,phone_number,password,date_created='NOW()'):
    """Creates user
    
        username (str): username for the new user
        phone_number (str): phone number for the new user
        password (str): password of the user
        date_created (datetime or timestamp): Date of creation for the account
        
        Should be called by user who wants to make account

        returns nothing
        """
    
    sql="""
        INSERT INTO users (username, name, phone_number, user_password, date_created) 
        VALUES (%s, %s, %s, %s,%s)
    """
    values = (username,name,phone_number,__hashPassword(password),date_created)
    try:
        exec_commit(sql,values)
        return True
    except:
        return False

def update_name(username,session_key, new_name,date_changed="NOW()"):
    """Updates the username of a user if the required conditions are met

        username (str): Username of user
        session_key (str): session_key of user updating
        new_nmae (str): The new name to be updated
        date_changed (str, optional): The date when the username was changed, default is "NOW()"

        Users should be able to call this to update their usernames

        Returns true if the username was successfully updated, false otherwise"""
    
    uid=check_session_key(username,session_key)
    if uid is not None:
        sql="""SELECT COUNT(user_id) FROM users WHERE user_id=%s
        AND ((name_last_changed + INTERVAL '6 months') <= %s OR name_last_changed IS NULL)"""
        can_change=exec_get_one(sql,(uid,date_changed))[0]
        if can_change==0:
            return False
        else:
            update_sql="""UPDATE users SET name=%s,
            name_last_changed=%s WHERE user_id=%s"""
            exec_commit(update_sql,(new_name,date_changed,uid))
            return True
    return False
    
def update_phone_number(username,session_key,new_phone_number):
    """Updates the phone_number of a user
    
        session_key(str): session_key of user changing phone-number
        new_phone_number(int): new phone-number
        
        used by users to change phone-number
        
        returns nothing"""
    
    uid=check_session_key(username,session_key)
    if uid is not None:
        sql="""UPDATE users SET phone_number=%s WHERE user_id=%s"""
        exec_commit(sql,(new_phone_number,uid))
        return True
    return False

def delete_user(username,session_key):
    """Deletes a user from the database based on their username

    username (str): The username of the user to be deleted

    Only administrators should be allowed to call this method

    Returns None"""

    uid=check_session_key(username,session_key)
    if uid is not None:
        sql="""DELETE FROM users WHERE user_id = %s """
        exec_commit(sql,(uid,))
        return True
    return False

def get_messages(username,session_key,sender=None,start=None,end=None,number=None,asc=False):
    """Retrieves messages between two users, optionally within a specific time range and a limited amount

        receiver (str): The username of the receiver
        sender (str, optional): The username of the sender. If provided, only retrieves messages between the two users
        start (str, optional): The start time for message retrieval
        end (str, optional): The end time for message retrieval
        number(int, optional): Number of messages that will be gathered, default none
        asc(boolean, optional): decids weather or not data will be sorted by date decensind or acending

        Only the receiver or sender should be allowed to view their conversation

        Returns a list of messages between the users"""

    rid=check_session_key(username,session_key)
    if rid is None:
        return None
    sql="""SELECT text_sent FROM direct_messages WHERE"""
    values=[rid]
    #Dynamically add to querey and values as needed
    if sender is None:
        sql+=" receiver_id=%s or sender_id=%s"
        values.append(rid)
    else:
        sid=get_user_id(sender)
        values.append(sid)
        values.append(rid)
        values.append(sid)
        sql+="""((receiver_id=%s AND sender_id=%s) OR (sender_id=%s AND receiver_id=%s))"""

    if start is not None:
        sql+=" AND time_sent >= %s"
        values.append(start)
    if end is not None:
        sql+=" AND time_sent <= %s"
        values.append(end)
    if asc:
        sql+=""" ORDER BY time_sent ASC"""
    else:
        sql+=""" ORDER BY time_sent DESC"""
    if number is not None:
        sql+=" LIMIT %s"
        values.append(number)

    return exec_get_all(sql,values)

def login(username,password):
    """Logs user in
    
        username(str): username of user
        password(str): password of user
        
        used by users who want to log in
        
        returns session key if succesfull, returns None else"""
    
    uid=exec_get_one("SELECT user_id FROM users WHERE username=%s and user_password=%s",(username,__hashPassword(password)))
    if uid is not None:
        token=secrets.token_urlsafe(64)
        exec_commit("UPDATE users SET session_key=%s WHERE user_id=%s",(f'{token}',uid))
        return token
    return None

def logout(username,session_key):
    """Logs user out
    
        session_key(str): session key of user
        
        used internally by server or db
        
        returns True if logout succesful, False else"""
    
    uid=check_session_key(username,session_key)

    if uid is not None:
        exec_commit("UPDATE users SET session_key=NULL WHERE user_id=%s",(uid,))
        return True
    return False

def is_suspended(uid,date='NOW()',community_name=None):
    """Checks if a user is currently suspended globally or communily based on a given date

        uid (int): The user id of the user to check
        date (datetime or timestamp, optional): The date to check the suspension against, default is 'NOW()'
        community_name(str): Community name where user is being checked for suspension. If left none, checks for global suspension

        Access restricted to authorized users who can view suspension statuses

        Returns true if the user is suspended, false otherwise"""
    sql="SELECT user_id FROM suspensions WHERE user_id=%s AND suspension_ends>%s "

    if community_name is None:
        #if anything is returns from suspension table, user is supended
        if exec_get_one(sql+"AND community_id IS NULL",(uid,date)) is not None:
            return True
        return False
    
    cid=get_community_id(community_name)
    if exec_get_one(sql+"AND community_id=%s",(uid,date,cid)) is not None:
        return True
    return False

def get_sender_reciever_ids(user1,user2):
    """Retrieves the user IDs for two given usernames.

        user1 (str): The first username
        user2 (str): The second username

        Used internally

        Returns: a tuple containing the user IDs of the sender and receiver"""

    select="SELECT user_id FROM users WHERE username = %s"

    #tuples are returned so index first one
    return exec_get_one(select,(user1,))[0],exec_get_one(select,(user2,))[0]

def send_direct_message(username,session_key,receiver,message,timestamp='NOW()',read=False):
    """Sends a direct message from one user to another

        sender (str): The username of the message sender
        receiver (str): The username of the message receiver
        message (str): The message content
        timestamp (datetime or timestamp, optional): The time the message was sent, default is 'NOW()'
        read (bool, optional): The read status of the message, default is False

        The sender must not be suspended to send messages

        Returns None"""
    
    sid=check_session_key(username,session_key)
    if sid is None:
        return None
    #testing to see is sender is supended
    if is_suspended(sid,timestamp):
        #silly goose
        print("You're suspended you silly goose")
        return None

    sql="""INSERT INTO direct_messages(sender_id,receiver_id,text_sent,time_sent,is_read)
    Values(%s,%s,%s,%s,%s)"""

    rid=get_user_id(receiver)

    values=(sid,rid,message,timestamp,read)

    exec_commit(sql,values)
