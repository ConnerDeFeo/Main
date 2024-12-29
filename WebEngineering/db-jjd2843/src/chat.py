from src.swen344_db_utils import *
import csv

def make_tables():
    """Base function for creating tables
        
        should only be used when initially creating the data basse or for testing
        
        returns nothing"""

    exec_commit("""DROP TABLE IF EXISTS suspensions, users, direct_messages, communities, channels, users_communities, channel_messages,users_channels CASCADE""")
    exec_sql_file('db-jjd2843/src/main.sql')

def get_user_id(username):
    """Gets a single users id
    
        usernsame (str): username of the User who's id is returned
        
        used only internally within other functions
        
        returns user_id"""
    
    return exec_get_one("SELECT user_id FROM users WHERE username = %s",(username,))[0]

def create_user(username,phone_number,date_created='NOW()'):
    """Creates user
    
        username (str): username for the new user
        phone_number (str): phone number for the new user
        date_created (datetime or timestamp): Date of creation for the account
        
        Should be called by user who wants to make account

        returns nothing
        """
    
    sql="""
        INSERT INTO users (username, phone_number, date_created) 
        VALUES (%s, %s, %s)
    """
    values = (username,phone_number,date_created)
    
    exec_commit(sql,values)

def delete_user(username):
    """Deletes a user from the database based on their username

    username (str): The username of the user to be deleted

    Only administrators should be allowed to call this method

    Returns None"""

    try:
        sql="""DELETE FROM users WHERE username = '%s' """
        exec_commit(sql,username)
    except:
        print("User does not exist")

def update_username(username, new_username,date_changed="NOW()"):
    """Updates the username of a user if the required conditions are met

        username (str): The current username of the user
        new_username (str): The new username to be updated
        date_changed (str, optional): The date when the username was changed, default is "NOW()"

        Users should be able to call this to update their usernames

        Returns true if the username was successfully updated, false otherwise"""

    sql="""SELECT COUNT(user_id) FROM users WHERE username=%s
    AND ((username_last_changed + INTERVAL '6 months') <= %s OR username_last_changed IS NULL)"""
    can_change=exec_get_one(sql,(username,date_changed))[0]
    if can_change==0:
        return False
    else:
        update_sql="""UPDATE users SET username=%s,
        username_last_changed=%s WHERE user_id=%s"""
        exec_commit(update_sql,(new_username,date_changed,get_user_id(username)))
        return True

def suspend_user(username,end,start='NOW()',community_name=None):
    """Suspends a user by inserting a suspension record in the database for either their account or a specific community

        username (str): The username of the user to be suspended
        end (str): The end date of the suspension
        start (datetime or timestamp, optional): The start date of the suspension, default is 'NOW()'
        community_name(str,optional): community that user will be suspended in, default None.
        No community name means suspension will be global

        Only administrators and community admins should be able to suspend users respectivley

        Returns none"""
    uid=get_user_id(username)
    if community_name is None:
        sql="""INSERT INTO suspensions(user_id,suspension_starts,suspension_ends)
        Values(%s,%s,%s)"""

        exec_commit(sql,(uid,start,end))
    else:
        sql="""INSERT INTO suspensions(user_id,community_id,suspension_starts,suspension_ends)
        Values(%s,%s,%s,%s)"""

        exec_commit(sql,(uid,get_community_id(community_name),start,end))

def unsuspend_user(username,community_name=None):
    """Unsuspends a user by setting their suspension end date to 'NOW'
        
        username (str): The username of the user to be unsuspended
        community_name(str): Community_name where user is being unsuspended, default none.
        No community name given means a lift from global suspension, not commmunity based ones. The inverse is true

        Only administrators and community admins should be able to unsuspend users

        Returns none"""
    
    uid=get_user_id(username)
    sql="""UPDATE suspensions SET suspension_ends=NOW()
        WHERE user_id=%s AND """
    if community_name is None:
        exec_commit(sql+"""community_id IS NULL""",(uid,))
    else:
        exec_commit(sql+"community_id=%s",(uid,get_community_id(community_name)))

def is_suspended(username,date='NOW()',community_name=None):
    """Checks if a user is currently suspended globally or communily based on a given date

        username (str): The username of the user to check
        date (datetime or timestamp, optional): The date to check the suspension against, default is 'NOW()'
        community_name(str): Community name where user is being checked for suspension. If left none, checks for global suspension

        Access restricted to authorized users who can view suspension statuses

        Returns true if the user is suspended, false otherwise"""
    
    uid=get_user_id(username)
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

def send_direct_message(sender,receiver,message,timestamp='NOW()',read=False):
    """Sends a direct message from one user to another

        sender (str): The username of the message sender
        receiver (str): The username of the message receiver
        message (str): The message content
        timestamp (datetime or timestamp, optional): The time the message was sent, default is 'NOW()'
        read (bool, optional): The read status of the message, default is False

        The sender must not be suspended to send messages

        Returns None"""
    
    #testing to see is sender is supended
    if is_suspended(sender,timestamp):
        #silly goose
        print("You're suspended you silly goose")
        return

    sql="""INSERT INTO direct_messages(sender_id,receiver_id,text_sent,time_sent,is_read)
    Values(%s,%s,%s,%s,%s)"""

    sid,rid=get_sender_reciever_ids(sender,receiver)

    values=(sid,rid,message,timestamp,read)

    exec_commit(sql,values)

def get_unread_count_direct_messages(receiver,sender=None):
    """Retrieves the count of unread messages for a user, optionally from a specific sender

        receiver (str): The username of the receiver
        sender (str, optional): The username of the sender. If provided, only counts unread messages from this sender

        Any user who wants to see their unread messages can use this

        Returns the count of unread messages"""

    rid=get_user_id(receiver)
    sql="""SELECT COUNT(text_sent) FROM direct_messages WHERE receiver_id=%s AND NOT is_read"""
    #checking if sender was specified
    if sender!=None :
        #add to querey if sender is specified
        sql+=" AND sender_id=%s"
        return exec_get_all(sql,(rid,get_user_id(sender)))[0][0]
    else:
        return exec_get_all(sql, (rid,))[0][0]

def get_messages(receiver,sender=None,start=None,end=None):
    """Retrieves messages between two users, optionally within a specific time range

        receiver (str): The username of the receiver
        sender (str, optional): The username of the sender. If provided, only retrieves messages between the two users
        start (str, optional): The start time for message retrieval
        end (str, optional): The end time for message retrieval

        Only the receiver or sender should be allowed to view their conversation

        Returns a list of messages between the users"""

    rid=get_user_id(receiver)
    sql="""SELECT text_sent FROM direct_messages WHERE"""
    values=[rid]
    #Dynamically add to querey and values as needed
    if sender is None:
        sql+="receiver_id=%s or sender_id=%s"
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

    return exec_get_all(sql,values)

def read_direct_messages(receiver,sender=None,start=None,end=None):
    """Marks messages as read between a receiver and an optional sender within a specified time range

        receiver (str): The username of the receiver
        sender (str, optional): The username of the sender
        start (str, optional): The start time for marking messages as read
        end (str, optional): The end time for marking messages as read

        Only the receiver should be able to mark messages as read

        Returns None"""

    rid=get_user_id(receiver)
    sql="""UPDATE direct_messages SET is_read=TRUE WHERE receiver_id=%s"""
    values=[rid]
    #Dynamically add to querey and values as needed
    if sender is not None:
        sid=get_user_id(sender)
        values.append(sid)
        sql+=""" AND sender_id=%s"""
    if start is not None:
        sql+=" AND time_sent >= %s"
        values.append(start)
    if end is not None:
        sql+=" AND time_sent <= %s"
        values.append(end)

    return exec_commit(sql,values)

def list_of_users(receiver):
    """Lists all users who have sent unread messages to the receiver, along with the count of unread messages

    receiver (str): The username of the receiver

    The receiver should be able to view their message senders and unread counts

    Returns a list of usernames and unread message counts, or None if no users are found"""

    rid=get_user_id(receiver)
    sql="""SELECT sender_id FROM direct_messages where receiver_id=%s AND is_read=FALSE GROUP BY sender_id"""
    
    sender_ids=exec_get_all(sql,(rid,))

    #checks if anyone has actaully sent a message
    if len(sender_ids[0])!=0:
        sql="SELECT username FROM users WHERE"
        values=[]
        #grap usernames to return
        for e in sender_ids:
            sql+=" user_id=%s OR"
            values.append(e)
        return exec_get_all(sql[:-3],values),get_unread_count_direct_messages(receiver)
    return None

def collect_csv_messages(filepath):
    """Collects messages from a CSV file and adds them to the database

        filepath(str): filepath to deisgnated csv file

        Used for seeding data or batch message import

        Returns None"""

    with open(filepath) as f:
        #used as list so it can be re-itterated over
        reader=list(csv.reader(f))
        #remove header
        reader.pop(0)
        users=[]
        #find two users
        for row in reader:
            if row[0]!='Both' and row[0] not in users:
                users.append(row[0])
            if len(users)==2:
                break

        messages=[]
        #cycle through file and adds messages to list, to then be injected in one batch
        for row in reader:
            if row[0]==users[0]:
                messages.append((users[0],users[1],','.join(row[1:]).strip()))
            elif row[0]==users[1]:
                messages.append((users[1],users[0],','.join(row[1:]).strip()))
                #I.E if row[0]==Both
            else:
                messages.append((users[0],users[1],','.join(row[1:]).strip()))
                messages.append((users[1],users[0],','.join(row[1:]).strip()))

        sql="""INSERT INTO direct_messages(sender_id,receiver_id,text_sent)
        VALUES"""
        args=[]
        for e in messages:
            sql+="(%s,%s,%s),"
            args.append(get_user_id(e[0]))
            args.append(get_user_id(e[1]))
            args.append(e[2])
        exec_commit(sql[:-1],args)

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

def get_community_channels(community_name):
    """Gets the channel id for all channels in a given community
    
        community_name(str): Name of the community
        
        used internally
        
        returns tuple containing all chnanel id_s that exist within a community
    """

    return [e[0] for e in exec_get_all("""SELECT channel_id FROM channels WHERE community_id=%s""",(get_community_id(community_name),))]

def join_community(username, community_name):
    """Adds a user to a community

        username(str): username of person joining a community
        community_name(str): name of community being joined

        The user must have permission to join the community

        Returns None"""
    
    uid=get_user_id(username)
    cid=get_community_id(community_name)

    exec_commit("INSERT INTO users_communities(user_id,community_id) VALUES(%s,%s)",(uid,cid))

    querey="""INSERT INTO users_channels(user_id,channel_id) VALUES"""
    args=[]
    for e in get_community_channels(community_name):
        querey+="(%s,%s),"
        args.append(uid)
        args.append(e)
    exec_commit(querey[:-1],args)

def leave_community(username, community_name):
    """Removes a user from a community

        username(str): username of person leaving community
        community_name(str): name of community user is leaving

        The user should be able to call this at any time

        Returns None"""

    uid=get_user_id(username)
    cid=get_community_id(community_name)

    exec_commit("DELETE FROM users_communities WHERE user_id=%s AND community_id=%s",(uid,cid))

    for e in get_community_channels(community_name):
        exec_commit("DELETE FROM users_channels WHERE user_id=%s and channel_id=%s",(uid,e))

def get_unread_count_community(username,community_name):
    """Gets unread messages count for a given user from a community

        username(str): username of person getting unread count
        community_name(str): name of community user is getting unread count from

        The user can call this at any time to get an unread count

        Returns total count of unread messages from a given community"""
    uid=get_user_id(username)
    cid=get_community_id(community_name)

    return exec_get_one("""SELECT unread_messages FROM users_communities WHERE user_id=%s AND community_id=%s""",(uid,cid))[0]

def get_unread_count_channel(username,community_name,channel_name):
    """Gets unread messages count for a given user from a channel in a community

        username(str): username of person getting unread count
        community_name(str): name of community where channel exists whithin
        channel_name: name of channel where user wants to see unread count

        The user can call this at any time to get an unread count

        Returns total count of unread messages from a given channel"""
    
    uid=get_user_id(username)
    com_id=get_community_id(community_name)
    cha_id=get_channel_id(com_id,channel_name)

    return exec_get_one("""SELECT unread_messages FROM users_channels WHERE user_id=%s AND channel_id=%s""",(uid,cha_id))[0]

def send_channel_message(username,community_name,channel_name,message,time="NOW()"):
    """Send message to a given channel as long as that person is not suspended

        username(str): username of person getting unread count
        community_name(str): name of community where channel exists whithin
        channel_name(str): name of channel where user wants to send message
        message(str): message being sent
        time(datetime or timestamp): time text is sent, default NOW() 

        The user can call this at any time to send a message as long as they are not suspended from the community

        returns true if message was sent and false if user is suspended"""
    
    if not is_suspended(username) and not is_suspended(username,community_name=community_name):
    
        uid=get_user_id(username)
        com_id=get_community_id(community_name)
        cha_id=get_channel_id(com_id,channel_name)

        exec_commit("""INSERT INTO channel_messages(channel_id,sender_id,text_sent,time_sent) VALUES(%s,%s,%s,%s)""",(cha_id,uid,message,time))

        #update unread count for all other users in community
        exec_commit("""UPDATE users_communities SET unread_messages=unread_messages+1 WHERE user_id <> %s""",(uid,))

        #update unread count for all other users specifically for that channel
        exec_commit("""UPDATE users_channels SET unread_messages=unread_messages+1 WHERE user_id <> %s""",(uid,))

        return True

    return False

def read_channel_mesesages(receiver,community_name,channel_name):

    """Reads messages from a specific channel in a specific community
    
        receiver(str): Name of the user gfetting the unread count
        community_name(str): name of community channel exists in
        channel_name(str): name of channel messages are being read from
        
        Any user will call this method when reading from a channel
        
        returns nothing"""
    
    rid=get_user_id(receiver)
    com_id=get_community_id(community_name)
    cha_id=get_channel_id(com_id,channel_name)

    unread_count=get_unread_count_channel(receiver,community_name,channel_name)

    #update unread count for channel
    sql_channel="""UPDATE user_channels SET unread_messages=0 WHERE user_id=%s AND channel_id=%s"""
    exec_commit(sql_channel,(rid,cha_id))

    #update unread count for community channel exists within
    sql_community="""UPDATE user_communities SET unread_messages=unread_messages-%s WHERE user_id=%s AND community_id=%s"""
    exec_commit(sql_community,(unread_count,rid,com_id))

def get_list_of_mentions(username):
    """Gets a list of mentions of a person in all communities they are a part off
    
        username(str): username of the user receiving the list of mentions
        
        can be used by any user looking for a list of their mentions
        
        returns list of messages that contain '@username' if any exist
    """

    sql="""SELECT text_sent FROM channel_messages WHERE text_sent LIKE %s
    AND channel_id IN """

    #starting with REGEX
    args=[f'%@{username}%',]

    #getting all comunities the user is a part of
    list_of_communities_ids=[e[0] for e in exec_get_all("SELECT community_id FROM users_communities WHERE user_id=%s",(get_user_id(username),))]

    if len(list_of_communities_ids)==0:
        return []

    #converting the ids to the name of the communities so it can work with previous function get_comunity_channels
    list_of_communities=[exec_get_one('SELECT community_name FROM communities WHERE community_id=%s',(e,)) for e in list_of_communities_ids]

    channel_ids=[]
    #itterate over list getting all channel ids that are associated with the given communitites and adding them to a list
    for e in list_of_communities:
        channel_ids.extend(get_community_channels(e))

    #(Im so proud of this one) adds the number of %s needed to deal with the list of channel ids
    sql+="("+','.join(['%s']*len(channel_ids))+")"

    args.extend(channel_ids)

    return exec_get_all(sql,args)

def full_text_search(word,direct_messages=False,channel_messages=False):
    """Gets all messages that contain a certain word or words from either direct messages, channel messages, or both
    
        word(str): word or words (put in: str & str & ... format) that will be searched for
        direct_messages(boolean, optional): decides weather or not direct_messages will be searched, default false
        channel_messages(boolean, optional): decides whether or not channel_messages will be searched, default false
        
        admins should be able to user this for data analytic purposes
        
        returns list of messages that contain a the word or a version of that word.
        If neither direct messages or channel messages is specififed, return searhc for both tables"""

    sql="""SELECT text_sent FROM {}
        WHERE to_tsvector('english',text_sent) @@ to_tsquery('english',%s)"""
    #check if both or neither was called
    word="&".join(word.split())
    if (not direct_messages and not channel_messages) or (direct_messages and channel_messages):
        messages_list=[e[0] for e in exec_get_all(sql.format('direct_messages'),(word,))]
        messages_list.extend([e[0] for e in exec_get_all(sql.format('channel_messages'),(word,))])
        return messages_list
    if direct_messages:
        return [e[0] for e in exec_get_all(sql.format('direct_messages'),(word,))]
    else:
        return [e[0] for e in exec_get_all(sql.format('channel_messages'),(word,))]

def active_summary(timestamp=None):
    """Generates active summary of both messages databases from a given time minus 30 days to time
    
        timestamp(str, optional): timestamp of when the summary will occur
        
        should be used by admins for data analytics
        
        returns list containing every community, the average amount of texts sent per day >=5
        in length, and the amount of users that have sent a text >=5 in length from the past month"""

    #this took me too long to figure out
    #had to google "DISTINCT"
    sql="""WITH stats AS (SELECT c.community_id,COUNT(cm.text_sent)/30.0 AS avg_num_messages, COUNT(DISTINCT cm.sender_id) AS num_users
    FROM channels c JOIN channel_messages cm ON c.channel_id=cm.channel_id 
    WHERE LENGTH(text_sent)>=5 AND cm.time_sent>={} - INTERVAL '30 days' 
    AND cm.time_sent<=%s GROUP BY c.community_id) SELECT com.community_name, s.avg_num_messages, s.num_users FROM stats s 
    JOIN communities com ON com.community_id=s.community_id"""

    #dynamically change return results depending on what was entered
    if timestamp is None:
        return [exec_get_all(sql.format('NOW()'),(timestamp,))]
    else:
        return exec_get_all(sql.format('TIMESTAMP %s'),(timestamp,timestamp))

def watch_suspended_users(current_timestamp='NOW()',start=None):
    """Retreives list of all users suspended from communities that have sent messages in a given time frame
    
        current_timestamp(str, optional): timestamp of the period when the function is called
        start(str, optional): timestamp of the earliest point where users suspended should be checked
        against sent messages
        
        should be used by admins for analytics
        
        returns list of users suspended from communities that have sent a message within a given time frame"""

    sql="""WITH messages AS (SELECT sender_id, time_sent FROM direct_messages UNION SELECT sender_id, time_sent from channel_messages), 
    users_suspended AS (SELECT u.username, u.user_id FROM users u JOIN suspensions s ON u.user_id=s.user_id  WHERE s.suspension_ends>=%s 
    AND s.community_id IS NOT NULL) SELECT us.username FROM users_suspended us JOIN messages m ON us.user_id=m.sender_id WHERE m.time_sent <= %s"""
    args=[current_timestamp,current_timestamp]
    if start is not None:
        sql+=" AND m.time_sent>=%s "
        args.append(start)
    sql += """GROUP BY us.username"""

    return [e[0] for e in exec_get_all(sql,args)]