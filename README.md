# Read Me

This is a Spring Boot application for managing wish lists. Users can register, log in, create wish lists, add wishes to wish lists, and view their wish lists.

## Functionality

The application provides the following functionality:

- Home Page: Accessible at the root URL http://localhost:8080/, displays a simple home page.
- Login: Users can log in with their email and password by accessing the `/login` endpoint. Users can submit their login credentials using a form and are redirected to their profile page upon successful login.
- Registration: Users can register a new account by accessing the `/register` endpoint. Users can submit their registration details using a form and are redirected to the login page upon successful registration.
- Profile: Users can view their profile page by accessing the `/profile` endpoint. The profile page displays the user's details, including their name and a list of their wish lists.
- Wish Lists: Users can view their wish lists by accessing the `/wishList` endpoint with a wish list ID as a query parameter. The wish list page displays the wish list details, including the list name and a list of wishes. Users can also create new wish lists by submitting a form.
- Wishes: Users can add wishes to their wish lists by accessing the `/wishList` endpoint with a wish list ID as a query parameter. The wish list page displays a form to add a new wish to the wish list.
