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
