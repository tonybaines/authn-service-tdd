
Background
----------
Our content management application is getting really popular and as it grows we need to move from simple authenticated access to something more sophisticated.

The Task

 * Write an AuthorisationService class that accepts a named resource and a user account then returns whether the user has access to the resource.
 * There are separate teams who will implement the services you’ll need (an AuthenticationService and a UserService), but they expect you to define the behaviour and API of their interfaces.
 * Any other services you’ll need (e.g. stores for defining the resource authorisation rules) are safe to stub out or use Collections ready for a later refactoring.
 * Consumers of your AuthorisationService will need to understand the error behaviour – make sure your tests document that too!

Work in pairs, swapping who codes roughly every 5 minutes.

Focus on

 * Learning the approach, rather than rushing to finish the stories
 * Taking small steps (imaging committing working code every 5 minutes)
 * The TDD cycle;
 ** Failing Test -> Passing Test -> Clean-up (Red, Green, Refactor)
 * Talking with your pair
 ** Coder: explain what you’re doing + why
 ** Pair: keeping to the process, taking a longer view

Stories

1.	Some resources are open to everyone, anonymous access is allowed
2.	Some resources require an authenticated user, but no more
3.	Some resources are only available to one or more named users
4.	Some resources are only available to members of a specific group
5.	The response in the case of a failed authorisation where groups must indicate the group(s) that do have access


