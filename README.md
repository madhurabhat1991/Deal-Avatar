# Deal Avatar

Deal Avatar is a simulation of www.slickdeals.net, a social commerce website featuring crowdsourced deals and coupons from retailers. Deal Avatar relies primarily on user generated content. The best deals and coupons are voted on by its active community, and Deal Avatar’s Deal Editor team curates the best of the best for its frontpage.


## Architecture

- IDE: NetBeans
- Application: HTML, CSS, BootStrap
- Framework: Java Server Faces (JSF)
- Web server: GlassFish server
- Business objects: Java
- Database: MySQL


## Business cases and pages
#### Sign up and login
- There are two roles – user and editor
- User needs to sign up and login to use the functions
- Editor needs to login to use the functions

#### Forums page
- On Forums page, user can create a new thread and see the list of threads. 
- By clicking on a thread user can check the thread details, vote up/down, post a comment
- A thread with votes >= 2 will be sent to editor for approval
- Once editor approves a thread, a thread becomes a deal

#### Front page
- Front page shows the best deals approved by the editor

#### Stores
- Stores page will display all the stores which has deals created by user
- Each store will display the deals for their store

#### Deal Alerts
- User can create a deal alert for which they would like to be notified

#### Notifications
- User will receive deal alert notifications. They can check the deals and mark it as read.
- Editor will receive deal approval notifications. They can approve/reject a deal.

#### Saved Items
- If a user likes a thread, user can save the item 
- Saved items page shows all the items saved for the user, can delete any.
