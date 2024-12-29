# Future Planning

## Adding reactions to the system

### Required tables

To add reactions in a proper manner, I would add a reaction table and modify both direct_messages and channel_messages.
Both direct_messages and channel_messages would gain a collumn called "reaction_id" with it's default set to NULL.
The reactions table itself would contain simply a serial primary key and a collumn called "reaction" which would be text
set default to ''. Only admins could add to the reaction table

### New Api methods

I would add an api methods that would allow admins to add a reaction to the reaction table.
Users would be given an API to update messages that are visibile to them (via their direct
and channel messages) so that reaction_id could be set to one of the ones on the table

### Changes in existing API

I would need to update the make_tables to remove the reactions table for testing purposes.
Besides this, no other API would have to be changed due to the nature of how I would implement it.

## Adding Threaded Conversations to the system

### Required tables

To add threaded conversations, I would update both direct and channel message tables.
Both would gain a collum called "reaction_id" that would reference another message in their
respective tables. Default would be set to NULL

### New Api methods

I would not nered to add any API methods for this, I would simply update both
send_direct_message() and send_channel_message().

### Changes in existing API

I would update both of the previously mentioned functions by adding parameters "reference_message"
defaulted to None. This would not be text, as two messages could have the same text, but would rather
be a timestamp. Although two timestamps technically can be identical, It would be the cleanest way to
implement it with my current knowledge of python and sql


