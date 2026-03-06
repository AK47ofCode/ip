# Sallybot
## Introduction

Sallybot is a chatbot programmed in Java, named after the Japanese-American voice actress & idol [_Sally Amaki_](https://en.wikipedia.org/wiki/Sally_Amaki).

Sallybot is a simple task management application that is controlled through a Command-Line Interface (CLI).

## Table of Contents:
- [Setup and Usage](#setup-and-usage)
- [Features](#features)
    - [Viewing commands: `help`](#viewing-commands-help)
    - [Add a Todo task: `todo`](#add-a-todo-task-todo)
    - [Add a Deadline task: `deadline`](#add-a-deadline-task-deadline)
    - [Add an Event task: `event`](#add-an-event-task-event)
    - [List all tasks: `list`](#list-all-tasks-list)
    - [Mark a task: `mark`](#mark-a-task-mark)
    - [Unmark a task: `unmark`](#unmark-a-task-unmark)
    - [Deleting a task: `delete`](#deleting-a-task-delete)
    - [Finding a task: `find`](#finding-a-task-find)
    - [Showing socials: `socials`](#showing-socials-socials)
    - [Showing Kiriko voiceline: `kiriko`](#showing-kiriko-voiceline-kiriko)
    - [Showing Carol voiceline: `carol`](#showing-carol-voiceline-carol)
    - [Exiting the program: `bye`](#exiting-the-program-bye)
- [Command Summary](#command-summary)
- [FAQ](#faq)
- [Known Issues](#known-issues)
- [Contact & Collaboration](#contact--collaboration)

## Setup and Usage
1. Ensure that you have Java 17 or above installed in your Computer.
   For macOS users, ensure that you have the precise JDK version prescribed
   [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
2. Download the latest `Sallybot.jar` file from [here](https://github.com/AK47ofCode/ip/releases).
3. Copy the file to the folder that you want to use as the home folder for your Task Manager.
4. Open Windows Command Prompt/PowerShell (Windows) or Terminal (macOS), `cd` into the folder 
   you put the jar file in. 
5. If you are running PowerShell, it is IMPORTANT you enter the following commands first:
   ```
   [System.Console]::OutputEncoding = [System.Text.Encoding]::UTF8
   [System.Console]::InputEncoding = [System.Text.Encoding]::UTF8
   ``` 
   If you are running Command Prompt, it is IMPORTANT you enter the following command first:
   ```
   chcp 65001
   ```
   This is to ensure that the UTF-8 characters (e.g. Japanese text, emojis) are displayed correctly.
6. Run the `java -jar Sallybot.jar` command to run the application.
7. You should see something like this:
    ```
    ________________________________________________________________________
      ____   __   __    __    _  _  ____   __  ____ 
     / ___) / _\ (  )  (  )  ( \/ )(  _ \ /  \(_  _)
     \___ \/    \/ (_/\/ (_/\ )  /  ) _ ((  O ) )(
     (____/\_/\_/\____/\____/(__/  (____/ \__/ (__) 
    
     🌸こんにちは🌸
     Hello there✨ I'm Sallybot! Always here to help hehe
     皆さんが日々ちょっとでも笑顔になる理由になりたいです❤
     I'm in the form of a bot because ᵗʰᵉ ᶦᵈᵒˡ ᵇᵘˢᶦⁿᵉˢˢ ᵈᵒᵉˢⁿ’ᵗ ᵖᵃʸ ᵐᵉ ᵉⁿᵒᵘᵍʰ
    
     What can I do for you today?
    ___________________________________________________________________________
   ```
8. Type the command in the command line and press Enter to execute it.
   (e.g. typing `help` and pressing Enter will open the help window.)
9. Refer to the [Features](#features) below for the details of each command.

## Features
### Viewing commands: `help`
Displays instructions on how to use the available commands in Sallybot and their formats.<br>
<b> Format: </b> `help` <br>
<b> Example: </b> `help` <br>
<b> Expected Output: </b>
```
help
	___________________________________________________________________________
	 はい! Here are the available commands:
	 1. help
	    	 Shows this help message.
	 2. list
	    	 Lists all tasks.
	 3. todo <description>
	    	 Adds a ToDo task.
	 4. deadline <description> /by <date>
	    	 Adds a Deadline task.
	 5. event <description> /from <start> /to <end>
	    	 Adds an Event task.
	 6. mark <index>
	    	 Marks the task at index as done.
	 7. unmark <index>
	    	 Marks the task at index as not done.
	 8. delete <index>
	    	 Deletes the task at index.
	 9. find <keyword>
	    	 Finds tasks that contain the keyword.
	 10. socials
	    	 Shows my socials and 22/7's socials! 🌸
	 11. kiriko
	    	 Shows a fun Kiriko voiceline! 🦊
	 12. carol
	    	 Shows a fun Carol voiceline! 👧
	 13. bye
	    	 Exits the program.
	___________________________________________________________________________
```

### Add a Todo task: `todo`
Adds a todo-type task with a description that does not require a date. <br>
<b> Format: </b> `todo [description]` <br>
<b> Example: </b> `todo join sports club` <br>
<b> Expected Output: </b>
```
todo join sports club
	___________________________________________________________________________
	 はい! I've added this task:
	   [T][ ] join sports club
	 Now you have 2 tasks in the list. 🌸
	___________________________________________________________________________
```

### Add a Deadline task: `deadline`
Adds a deadline-type task with a deadline. <br>
The date should be in the format `d/M/yyyy HHmm` (e.g. `9/3/2026 2100` for 9 March 2026, 9pm), 
or `yyyy-MM-dd HH:mm` (e.g. `2026-03-09 2100` for 9 March 2026, 9pm), even though dates such as
`Monday 9pm` are also accepted. The time must be in 24-hour format. <br>
<b> Format: </b> `deadline [description] /by [date]` <br>
<b> Example: </b> `deadline weekly quiz /by 9/3/2026 2100` <br>
<b> Expected Output: </b>
```
deadline weekly quiz /by 9/3/2026 2100
	___________________________________________________________________________
	 はい! I've added this task:
	   [D][ ] weekly quiz (by: Mar 09 2026 9:00pm)
	 Now you have 3 tasks in the list. 🌸
	___________________________________________________________________________
```

### Add an Event task: `event`
Adds an event-type task with a time range. <br>
The date should be in the format `d/M/yyyy HHmm` (e.g. `9/3/2026 2100` for 9 March 2026, 9pm),
or `yyyy-MM-dd HH:mm` (e.g. `2026-03-09 2100` for 9 March 2026, 9pm), even though dates such as
`Monday 9pm` are also accepted. The time must be in 24-hour format. <br>
<b> Format: </b> `èvent [description] /from [start date] /to [end date]`
<b> Example: </b> `event Countdown to end of 2027 /from 3/4/2026 0000 /to 31/12/2027 2359` <br>
<b> Expected Output: </b>
```
event Countdown to end of 2027 /from 3/4/2026 0000 /to 31/12/2027 2359
	___________________________________________________________________________
	 はい! I've added this task:
	   [E][ ] Countdown to end of 2027 (from: Apr 03 2026, to: Dec 31 2027 11:59pm)
	 Now you have 4 tasks in the list. 🌸
	___________________________________________________________________________
```

### List all tasks: `list`
Displays all the tasks stored in `Sallybot.txt`. <br>
<b> Format: </b> `list` <br>
<b> Example: </b> `list` <br>
<b> Expected Output: </b>
```
list
	___________________________________________________________________________
	 はい! Here are the tasks in your list:
	 1.[T][X] read book
	 2.[T][ ] join sports club
	 3.[D][ ] weekly quiz (by: Mar 09 2026 9:00pm)
	 4.[E][ ] Countdown to end of 2027 (from: Apr 03 2026, to: Dec 31 2027 11:59pm)
	___________________________________________________________________________
```

### Mark a task: `mark`
Marks a task specified by its index (an integer that is 1 or above) as completed. <br>
<b> Format: </b> `mark [index of task]`<br>
<b> Example: </b> `mark 4` <br>
<b> Expected Output: </b>
```
mark 4
	___________________________________________________________________________
	 はい! I've marked your task as done:
	 [E][X] Countdown to end of 2027 (from: Apr 03 2026, to: Dec 31 2027 11:59pm)
	___________________________________________________________________________
```

### Unmark a task: `unmark`
Marks a task specified by its index (an integer that is 1 or above) as incomplete. <br>
<b> Format: </b> `unmark [index of task]`<br>
<b> Example: </b> `unmark 1` <br>
<b> Expected Output: </b>
```
unmark 1
	___________________________________________________________________________
	 はい! I've marked your task as not done:
	 [T][ ] read book
	___________________________________________________________________________
```

### Deleting a task: `delete`
Deletes a task specified by its index (an integer that is 1 or above) from the list. <br>
<b> Format: </b> `delete [index of task]`<br>
<b> Example: </b> `delete 1` <br>
<b> Expected Output: </b>
```
delete 1
	___________________________________________________________________________
	 はい! I've deleted this task:
	 [T][ ] read book
	 Now you have 3 in the list.
	___________________________________________________________________________
```

### Finding a task: `find`
Finds a task using (a) keyword(s). <br>
<b> Format: </b> `find [keyword(s)]`<br>
<b> Example: </b> `find end of 2027` <br>
<b> Expected Output: </b>
```
find end of 2027
	___________________________________________________________________________
	 はい! Here are the tasks that match your search for "end of 2027":
	 1.[E][X] Countdown to end of 2027 (from: Apr 03 2026, to: Dec 31 2027 11:59pm)
	___________________________________________________________________________
```

### Showing socials: `socials`
Shows Sallybot's socials and 22/7's socials! 🌸 <br>
(Pardon the shameless promotion HAHAHAHA, but if you do check them out then that's great!) <br>
<b> Format: </b> `socials` <br>
<b> Example: </b> `socials` <br>
<b> Expected Output: </b>
```
socials
	___________________________________________________________________________
	 If you want to know more about me, check out my socials! 🌸
	 No not as Sallybot HAHA, but as Sally Amaki!
	 X/Twitter: https://x.com/sally_amaki
	 Instagram: https://www.instagram.com/sallyamaki/
	 TikTok: https://www.tiktok.com/@sally_amaki
	 YouTube: https://www.youtube.com/@sallyamakiofficial

	 Please do check out 22/7's socials too! 🌸
	 22/7 is the idol group that I am a part of, and we have lots of fun content on our socials!
	 X/Twitter: https://x.com/227_staff
	 YouTube: https://www.youtube.com/c/227SMEJ
	 Official Website: https://www.nanabunnonijyuuni.com/

	 Oh and please don't also forget to see my Anime English Club podcast! 🌸
	 YouTube: https://www.youtube.com/@anime-english-club
	 Spotify: https://open.spotify.com/show/3cc98Fciw33tyxZe636cKr
	___________________________________________________________________________
```

### Showing Kiriko voiceline: `kiriko`
Shows a fun Kiriko voiceline! 🦊 <br>
This is a reference to the popular character Kiriko from the game Overwatch, voiced by Sally Amaki! <br>
<b> Format: </b> `kiriko` <br>
<b> Example: </b> `kiriko` <br>
<b> Expected Output: </b>
```
kiriko
	___________________________________________________________________________
	 Let the Kitsune guide you! 🦊
	___________________________________________________________________________
```

### Showing Carol voiceline: `carol`
Shows a fun Carol voiceline! 👧 <br>
This is a reference to a meme featuring the bilingual character Carol Olston from the anime Tomo-chan is a Girl!, 
voiced by Sally Amaki in both the original Japanese voiceover and English dub! <br>
<b> Format: </b> `carol` <br>
<b> Example: </b> `carol` <br>
<b> Expected Output: </b>
```
carol
	___________________________________________________________________________
	 Hey.
	 Stop doing that.
	 https://imgur.com/a/hey-stop-doing-that-t4qaboS
	___________________________________________________________________________
```

### Exiting the program: `bye`
Exits Sallybot. <br>
Poor Sally, she has to go back to doing her idol business that doesn't pay her enough after this... 😢 <br>
<b> Format: </b> `bye` <br>
<b> Example: </b> `bye` <br>
<b> Expected Output: </b>
```
bye
	___________________________________________________________________________
	 じゃあね👋
	 See you later!
	___________________________________________________________________________
```

## Command Summary

| Action                | Format, Examples                                                                                                                      |
|-----------------------|---------------------------------------------------------------------------------------------------------------------------------------|
| Help                  | `help`                                                                                                                                |
| Add Todo              | `todo [description]` e.g. `todo join sports club`                                                                                     |
| Add Deadline          | `deadline [description] /by [date]` e.g. `deadline weekly quiz /by 9/3/2026 2100`                                                     |
| Add Event             | `event [description] /from [start date] /to [end date]` e.g. `event Countdown to end of 2027 /from 3/4/2026 0000 /to 31/12/2027 2359` |
| List                  | `list`                                                                                                                                |
| Mark                  | `mark [index of task]` e.g. `mark 4`                                                                                                  |
| Unmark                | `unmark [index of task]` e.g. `unmark 1`                                                                                              |
| Delete                | `delete [index of task]` e.g. `delete 1`                                                                                              |
| Find                  | `find [keyword(s)]` e.g. `find end of 2027`                                                                                           |
| Show Socials          | `socials`                                                                                                                             |
| Show Kiriko voiceline | `kiriko`                                                                                                                              |
| Show Carol voiceline  | `carol`                                                                                                                               |
| Exit                  | `bye`                                                                                                                                 |

## FAQ
WIP. Stay tuned.

## Known Issues
Currently, there are no known issues or major bugs. However, I am not 100% sure if that is the case for macOS
users as well, as I am not a macOS user (which is funny considering Sally has a MacBook and doesn't seem to use
Windows LOL). If you discover any issues, please do inform me via email at [2003lky@gmail.com](mailto:2003lky@gmail.com)
or any of my other socials (e.g. LinkedIn, Telegram, GitHub) as listed below. Thank you!

## Contact & Collaboration
If you want to collaborate with me or just want to have a chat, feel free to drop me a follow on my socials and/or DM me! Let's connect! <br>
[LinkedIn](https://www.linkedin.com/in/kuan-yi-lee-a72a111a2/) |
[Email](mailto:2003lky@gmail.com) |
[Telegram](https://t.me/albertnoteinstein) |
[GitHub](https://github.com/AK47ofCode)