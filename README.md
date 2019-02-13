# HowToWebDriver
This repo contains a bunch of examples on how to use WebDriver 3.x using it's Java bindings.

To run the examples first make sure to have a test site set up:
- cd into the "testsite" directory
- run: ```python -m SimpleHTTPServer```

Examples (recommended order of reading/running):
- [Start.java](src/examples/Start.java) - how to start a Firefox instance and point it to your test site
- [FindThings.java](src/examples/FindThings.java) - a bunch of methods to get different information from a website (using xpaths and CSS paths)
- [ChangeThings.java](src/examples/ChangeThings.java) - clicking and typing
- [WaitForThings.java](src/examples/WaitForThings.java) - fluently waiting for events on the site (also OPTION handling)
- [MoveThings.java](src/examples/MoveThings.java) - more advanced mouse related actions (action chaining)
- [BreakThings.java](src/examples/BreakThings.java) - common WebDriver exceptions handling
- [ScriptThings.java](src/examples/ScriptThings.java) - using custom JavaScript with WebDriver
- [TestThings.java](src/examples/TestThings.java) - simple TestNG integration
