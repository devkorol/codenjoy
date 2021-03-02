Feature: Admin page
  Admin page main features

Scenario: User cant open admin page but Admin can
  Given User registered with name 'Stiven Pupkin', email 'user1@mail.com', password 'password1', city 'Moon', tech skills 'Java', company 'Home', experience '10 years'

  When Login as 'user1@mail.com' 'password1'
  When Try open Admin page
  Then Error page opened with message 'Something wrong with your request. Please save you ticker number and ask site administrator.'

  When Login as 'admin@codenjoyme.com' 'admin'
  When Try open Admin page
  Then Admin page opened with url '/admin?room=first'

Scenario: Admin can close/open registration
  Given Login to Admin page
  Then Registration is active

  When Click Close registration
  Then Registration was closed

  When Click logout

  When Open registration page
  Then See 'Registration was closed' registration error
  And There is no controls on registration form

  When Open login page
  Then See 'Registration was closed' login error
  And There is no controls on login form

  When Open admin login page
  And Try to login as 'admin@codenjoyme.com' with 'admin' password
  Then Admin page opened with url '/admin?room=first'

  When Try open Admin page
  Then Registration was closed

  When Click Open registration
  Then Registration is active

  When Click logout

  When Open registration page
  And Try to register with: name 'Stiven Pupkin', email 'user1@mail.com', password 'password1', city 'Moon', tech skills 'Java', company 'Home', experience '10 years', game 'first'
  Then Board page opened with url '/board/player/<PLAYER_ID>?code=<CODE>&game=first'
  Then User registered in database as 'Registration.User(email=user1@mail.com, id=<PLAYER_ID>, readableName=Stiven Pupkin, approved=1, code=<CODE>, data=Moon|Java|Home|10 years)'

  When Open login page
  And Try to login as 'user1@mail.com' with 'password1' password in game 'first'
  Then Board page opened with url '/board/player/<PLAYER_ID>?code=<CODE>&game=first'