# HowToWebDriver
This repo contains a bunch of examples on how to use WebDriver 3.x using it's Java bindings.

To run the examples first make sure to have a test site set up:
- cd into the "testsite" directory
- run: ```python -m SimpleHTTPServer```

Examples (recommended order of reading/running):
- Start.java - how to start a Firefox instance and point it to your test site
- FindThings.java - a bunch of methods to get different information from a website (using xpaths and CSS paths)
- ChangeThings.java - clicking and typing
- MoveThings.java - more advanced mouse related actions (action chaining)
- BreakThings.java - common WebDriver exceptions handling
- ScriptThings.java - using custom JavaScript with WebDriver
- TestThings.java - simple TestNG integration
