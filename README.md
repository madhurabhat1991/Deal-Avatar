# Deal Avatar

Deal Avatar is a simulation of www.slickdeals.net, a social commerce website featuring crowdsourced deals and coupons from retailers. Deal Avatar relies primarily on user generated content. The best deals and coupons are voted on by its active community, and Deal Avatar’s Deal Editor team curates the best of the best for its frontpage.


## Architecture

- IDE: NetBeans
- Application: HTML, CSS, Bootstrap
- Framework: Java Server Faces (JSF)
- Web server: GlassFish server
- Business objects: Java
- Database: MySQL


## Business cases and pages
#### Sign up and login
- There are two roles – user and editor
- User needs to sign up and login to use the functions
- Editor needs to login to use the functions

![image](https://user-images.githubusercontent.com/70220146/119933822-6fda6180-bf4a-11eb-8fbf-1348fe0fd961.png)

#### Forums page
- On Forums page, user can create a new thread and see the list of threads. 
- By clicking on a thread user can check the thread details, vote up/down, post a comment
- A thread with votes >= 2 will be sent to editor for approval
- Once editor approves a thread, a thread becomes a deal

![image](https://user-images.githubusercontent.com/70220146/119934151-fb53f280-bf4a-11eb-9e9d-70b0528e1390.png)

#### Front page
- Front page shows the best deals approved by the editor

![image](https://user-images.githubusercontent.com/70220146/119933954-a7e1a480-bf4a-11eb-84ea-6061b5b86463.png)

#### Stores
- Stores page will display all the stores which has deals created by user
- Each store will display the deals for their store

![image](https://user-images.githubusercontent.com/70220146/119934308-30604500-bf4b-11eb-9123-33a3e9091af7.png)

#### Deal Alerts
- User can create a deal alert for which they would like to be notified

![image](https://user-images.githubusercontent.com/70220146/119934217-145ca380-bf4b-11eb-8502-cee5f80294de.png)

#### Notifications
- User will receive deal alert notifications. They can check the deals and mark it as read.
- Editor will receive deal approval notifications. They can approve/reject a deal.

![image](https://user-images.githubusercontent.com/70220146/119934600-a6fd4280-bf4b-11eb-9897-ac6dc4f0ab93.png)

#### Saved Items
- If a user likes a thread, user can save the item 
- Saved items page shows all the items saved for the user, can delete any.

![image](https://user-images.githubusercontent.com/70220146/119934404-5a196c00-bf4b-11eb-8ef8-9ee924b18a89.png)
