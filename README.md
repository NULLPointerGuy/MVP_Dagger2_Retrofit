# Todo App
Simple todo application that uses MVP architecture, JUnit for unit testing
and Expresso for integration testing.

# Structure
-----------
![alt tag](https://github.com/NULLPointerGuy/Todo/blob/master/assets/overview-arch.png)

the above image depicts the basic overview of the app.

# Usage of Dagger in the app.
-------------
![alt tag](https://github.com/NULLPointerGuy/Todo/blob/master/assets/DaggerOverview.png)

Since Picasso, Context and Shared Preference are being shared across all 3 views,
we have decided to put it in TodoAppModule which resides inside corecommon.
Same reason goes for the api module being resided inside corecommon.
And also because TodoApp class(which extends application) is used commonly across the views for,
SDK initialisations's etc.It's wise to keep it inside corecommon.
Naturally because of TodoApp and Module class's being resided in corecommon, TodoComponent
which is the main entry point for Dagger's graph, resides in corecommon.
Because of this reason alone other module components cannot be subcomponent of TodoComponent.
