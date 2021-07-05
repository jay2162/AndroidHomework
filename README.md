Requirements
Language: Kotlin
Touchpoints:
Networking
Maps
Design patterns
Dev Tool Utilization
---------------------------------------
API Spec
Refer the API Spec below
Note: The API is hosted on a free service and the first time connecting will be slow.
-----------------------------------------
Create an app based on the wireframe.
The app should include:
a working login
required: Basic email validation
Nice to have: Simple password strength support and indicators
a working registration page
required: Basic email validation
Nice to have: Simple password strength support and indicators
----------------------------------
Android Homework ProjectAPI Spec
Base URL: https://blooming-stream-45371.herokuapp.com/
Authentication
POST
/api/v2/people/authenticate
Input
{
"email": "email@example.com", // (required)
"password": "password" // (required)
}
Email
Email
••••••
Password
Login
Email
Email
Full Name
Full Name
Sign Up
Login Sign Up
Password
Email
Password Again
••••••
Sign Up
- Should include basic email validation
- Should include null field validation
- Displays error pop over alert for errors from
frontend and backend
- The active input should be on screen while
keyboard is visible. Should not be behind the
keyboard
Nice to Have
- Password strength validation
Login
- Should include basic email validation
- Should include null field validation
- Displays error pop over alert for errors from
frontend and backend
- The inputs should not be off screen while the
keyboard is visible.
Nice to Have
- Password strength validation
Map
Page Map Page
Required:
- Shows user location on map (Default pin is acceptable)
- Shows additional map makers, using a custom
image or technique. Must not be the default google map makers, once complete.
- Includes a button that when tapped centers the map on top of the users location
- Tapping a custom marker should display a pop- over that displays one of the values of the marker
in it.
- The map should center and focus on the
tapped pin
- Tapping the map should dismiss the marker.
Nice to have:
- Set yourself up such that you could easily pipe in data from an API or external source, rather than hardcoded or baked in with the app.
Map Profile
Map
This will use the Google maps map
component and should display the users current location. It should also display an array of Map Markers with a
custom marker, of your choice, as the pin. It simply must not be the default marker
Profile Page Profile Page
Required:
- Add placeholder position for a user uploaded profile image
- Display the current name / email of the logged in user
- Display in human readable format the number of days that the account has been created.
Map Profile
Sign Up
Instead
Profile Name
Logout
22 Days OldHEADERS
{
    'Content-Type':'application/json'
}
Output
{
  "authentication_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Ii1MWE44VXJ4bH
  "person": {
   "key": "-LXN8UrxlvaSjYwVYlz-",
   "display_name": "Brent",
   "role": {
   "key": "oneOf(user||admin)",
   "rank": 0 // n > 0 <= 99
   }
  }
}
Registration
POST
/api/v2/people/create
Input
{
"display_name": "Display_Name", // (required)
"email": "email@example.com", // (required)
"password": "password", // (required)
}
Output
{
  "authentication_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Ii1MWE44VXJ4bH
  "person": {
   "key": "-LXN8UrxlvaSjYwVYlz-",
   "display_name": "Brent",
   "role": {
   "key": "oneOf(user||admin)",
   "rank": 0 // n > 0 <= 99
   }
  }
}
User
GET
/api/v2/people/me // Detail Route
/api/v2/people/<person_key> // Detail route
// Results will vary depending on the kind of information you are querying and
// the roles and permissions you currently have.
// The object below is the biggest object a USER role can obtainHEADERS
{
"Authorization": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Ii1MWE44VXJ4bHZhU2p
"Content-Type": "application/json"
}
Output
{
  "created_at": "2019-01-29T05:55:25.608Z",
  "display_name": "Brent",
  "email": "brent@ridecell.com",
  "key": "-LXNAIE3uveXDRVvfLFD",
  "role": {
   "key": "user",
   "rank": 0
  },
  "updated_at": "2019-01-29T06:23:47.341Z"
}
Password Management
POST
/api/v2/people/reset_password
Input
{
"email": "email@example.com"
}
Output
{
"message": "We have sent you a password reset link to your email!"
}
GET
/api/v2/people/password_requirements
Output
{
   "require_lowercase": true,
   "last_x_passwords": 5,
   "require_number": true,
   "require_special": true,
    "max_chars": 1024,
    "require_uppercase": true,
    "min_chars": 8
}
Vehicles APIGET
/api/v2/vehicles
HEADERS
{
   "Authorization": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Ii1MWE44VXJ4bHZhU2p
   "Content-Type": "application/json"
}
Output
[
   {
   "id": 84,
   "is_active": true,
   "is_available": true,
   "lat": 37.779816,
   "lng": -122.395447,
   "license_plate_number": "7LVF807",
   "remaining_mileage": 91,
   "remaining_range_in_meters": 146000,
   "transmission_mode": "park",
   "vehicle_make": "Cybertruck",
   "vehicle_pic_absolute_url": "https://d16vxu8318b851.cloudfront.net/uploads/veh
   "vehicle_type": "Tesla",
   "vehicle_type_id": 11
   }
