# Nearby-Cafe
Nearby Cafes

I have used MVP (Model View Presenter) pattern for designing the app.

The model is responsible for handling the data.
In this case I have packaged all the model related files under model package.

The view is responsible for handling the UI updates and changes. It should only care about the changes to be made to the UI and should not have business logic or data manipulation inside it.

The Presenter is responsible for handling the business logic of the app. It abstracts the view from model and works as an intermediator and also helps in handling the business logic.
The view and presenter can talk to each other by calling methods defined under Contract interface. The model just works on requests and deliver the results to the callbacks.

The activity has been reduced to just handle the lifecycle methods and the fragment is what is mainly responsible for UI changes.

I have used Volley library to make network requests.

For location updates there could have been two approches :
to get the last known location(which is immediately available)
or to use a location listener (which informs only when there is an update in location)

I have used location listener as the requirement was to update the list when the location changes.

On configuration change I am not saving the data intentionally. Not sure whether saving or not saving is a requirement. (If we dont save we have fresh data always but downside is we have to wait for all the calls again)

