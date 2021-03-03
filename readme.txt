Overview:
I have created a small Java tool that allows you to paste in the NPC/Monster stat block. The tool will then parse the data and paste it into a Pathfinder Community Sheet, to create a fully fledged Roll20 token with all the standard macros of the PF community sheet enabled. The tool will be free to use. Released to https://kroythegm.itch.io/roll20-pathfinder-npc-import-tool

Features:
	Supports the "Pathfinder Community" along with "Pathfinder by Roll20" sheet
	Converts most of the NPC stats.
	Pasting from PDF's may require some manual correction due to display/formatting creating line breaks.
	Requires Java 1.8 or higher
	Tested on Windows & Mac

Tutorial: https://itch.io/t/768563/how-to-a-tutorial
Tips and Tricks: https://itch.io/t/769898/tips-and-tricks

Issues parsing
1) Bad Ecology location
	https://www.d20pfsrd.com/bestiary/monster-listings/aberrations/clamor

Sources	used in testing
	https://www.d20pfsrd.com/bestiary/npc-s/
	https://www.d20pfsrd.com/bestiary/
	http://www.thegm.org/npcs.php
	https://aonprd.com/Monsters.aspx
	http://www.monsteradvancer.com/send/monster/quickenedMonsterAdvancer.ma?pfMode

Parsing tips
Things to look out for
  Grouping of knowledge skills like https://www.d20pfsrd.com/bestiary/monster-listings/aberrations/great-old-ones/great-old-one-hastur
  
Pasting Tips
Melee that have the word "trip" such as Melee triple-jawed bite +26 will be parsed into a CMB attack. replace "trip" with "three" to avoid
Validate melee, sometimes the first attack +0 to bonus. This is due to +5 being in the name of the weapon like +5 icy burst sword. I have filtered out some of them, but not all.
Do not click the "Parse" button again, it will inflate the character and lag the game.

Issues
https://www.d20pfsrd.com/bestiary/monster-listings/outsiders/demon/demon-felius +1+1 two ended weapon.

Started project 2020-04-14
