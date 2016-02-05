# RegionFX

RegionFX is a Spigot plugin which hooks into WorldGuard to provide constant effects applied to regions.

## Commands

##### /regionfx create [name] [effect] [level]
  Creates a new EffectRegion with the provided values

##### /regionfx delete [name]
  Will Delete the provided EffectRegion name

##### /regionfx list
  Displays a list of all active Effect Regions

##### /regionfx potions
  Displays a list of all possible Potion Effect types for EffectRegions
 
**Parameters Key:**
 - name:  The name of an existing WorldGuard Region
 - effect: A valid potion effect name (You can view all valid potions names with /regionfx potions)
 - level: The level in which the potion effect can be modified (Example: 90 would make a jump potion level 90)



### Permissions
  `regionfx.admin` - Access to all commands
