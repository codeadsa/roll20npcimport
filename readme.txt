Started project 2020-04-14

Issues parsing
1) Bad Ecology location
	https://www.d20pfsrd.com/bestiary/monster-listings/aberrations/clamor

Sources	
	https://www.d20pfsrd.com/bestiary/npc-s/
	https://www.d20pfsrd.com/bestiary/
	http://www.thegm.org/npcs.php
	https://aonprd.com/Monsters.aspx
	http://www.monsteradvancer.com/send/monster/quickenedMonsterAdvancer.ma?pfMode
graphic of current development timeline.


Parsing tips
Things to look out for
  Grouping of knowledge skills like https://www.d20pfsrd.com/bestiary/monster-listings/aberrations/great-old-ones/great-old-one-hastur
  
Pasting Tips
Melee that have the word "trip" such as Melee triple-jawed bite +26 will be parsed into a CMB attack. replace "trip" with "three" to avoid
Validate melee, sometimes the first attack +0 to bonus. This is due to +5 being in the name of the weapon like +5 icy burst sword. I have filtered out some of them, but not all.
Do not click the "Parse" button again, it will inflate the character and lag the game.

Issues
https://www.d20pfsrd.com/bestiary/monster-listings/outsiders/demon/demon-felius +1+1 two ended weapon.


Mac
When opening get error message
