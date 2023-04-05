# Garage-Sale-Application
This is a backend RESTful application developed in Spring Boot and it is connected to a MySQL database. This app was tested with JUnit and Mockito.

  This app allows you too add a new user, to add a new product, to see all available products and to make an order. 
  For creating a new user, you must introduce a name and a valid email.
  For making a new order, you must pass on a user, the id of the products you want to buy and the details of the card. By details of the card we understand a card holder name, a valid card date, a valid card number and a cvv. 
  After making an order, we receive back a receipt containing info about our order. There are several receipt types, which aim to treat 
different order status. For example, if the card number is not a valid one, the receipt contains a message and the card number. Moreover, if the order was successful, then the receipt contains the purchased products names, the total sum, and the user that made the order. In this case, the order is saved in the database, containing the purchased products id and other
usefull info.
