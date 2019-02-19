# HowToWebDriver
This repo contains a bunch of examples on how to use WebDriver 3.x using it's Java bindings.

To run the examples first make sure to have a test site set up:
- cd into the "testsite" directory
- run: ```python -m SimpleHTTPServer```

Examples (recommended order of reading/running):
- [Start.java](src/examples/ex1_Start.java) - how to start a Firefox instance and point it to your test site
- [FindThings.java](src/examples/ex2_FindThings.java) - a bunch of methods to get different information from a website (using xpaths and CSS paths)
- [ChangeThings.java](src/examples/ex3_ChangeThings.java) - clicking and typing
- [WaitForThings.java](src/examples/ex4_WaitForThings.java) - fluently waiting for events on the site (also OPTION handling)
- [MoveThings.java](src/examples/ex5_MoveThings.java) - more advanced mouse related actions (action chaining)
- [BreakThings.java](src/examples/ex6_BreakThings.java) - common WebDriver exceptions handling
- [ScriptThings.java](src/examples/ex7_ScriptThings.java) - using custom JavaScript with WebDriver
- [TestThings.java](src/examples/ex8_TestThings.java) - simple TestNG integration


:arrow_right: Note:

My company decided to release an addon I designed as open source. This addon was created to allow fetching console.log entries remotely using WebDriver 3.x (while Mozilla struggles to fix https://github.com/mozilla/geckodriver/issues/284).

With my approach, you don't need to access the stdout of geckodriver (which is quite handy, when you use Selenium Grid).

See https://github.com/hurracom/WebConsoleTap
