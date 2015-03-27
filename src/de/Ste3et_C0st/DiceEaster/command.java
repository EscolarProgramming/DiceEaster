package de.Ste3et_C0st.DiceEaster;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class command implements CommandExecutor {
	public command() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(sender instanceof Player){
			if(cmd.getName().equalsIgnoreCase("easter")){
				Player p = (Player) sender;
				Boolean isInWorld = true;
				if(!main.getInstance().world.contains(p.getWorld().getName())){isInWorld = false;}
				String help = "§2==================================\n";
				if(isInWorld && p.hasPermission("Easter.Admin")){
					help += "§2/easter give | §7You becom the Easter Eggs\n";
					help += "§2/easter remove | §7You can Pickup one Easter Egg\n";
					help += "§2/easter info | §7You become an Info about the Eggs\n";
					help += "§2/easter setwin <double> | §7Set the win\n";
					help += "§2/easter world | §7Set the World as Easter World\n";
					help += "§2/easter bunny | §7Spawn easter Bunny\n";
					if(p.hasPermission("Easter.Admin")){
					help += "§2/easter top (side/output) | §7Display TopTen\n";
					}else{
					help += "§2/easter top (side) | §7Display TopTen\n";
					}
					
					help += "§2Easter Eggs: §7" + main.egglist.size() + "\n";
					help += "§2==================================";
				}else if(!isInWorld && p.hasPermission("Easter.Admin")){
					help += "§2/easter world\n";
					help += "§2==================================";
				}else if(isInWorld){
					help += "§2Easter Eggs: §7" + main.getInstance().getPlayerScore(p) + "/" + main.egglist.size() + "\n";
					help += "§2==================================";
				}else{
					help = "";
				}
				
				if(args.length == 0){
					p.sendMessage(help);
					return true;
				}else if(args.length == 1){
					if(args[0].equalsIgnoreCase("top")){
						if(p.hasPermission("Easter.Top")){
							new topTen(p, 1);
							return true;
						}else{
							p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("NoPermissions"));
							return true;
						}
					}else if(args[0].equalsIgnoreCase("world")){
						if(p.hasPermission("Easter.Admin")){
							main.getInstance().world.add(p.getWorld().getName());
							p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("World"));
							
							main.getInstance().getConfig().set("DiceEasterEggs.Worlds", main.getInstance().world);
							main.getInstance().saveConfig();
							return true;
						}else{
							p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("NoPermissions"));
							return true;
						}
					}else if(args[0].equalsIgnoreCase("info")){
						if(p.hasPermission("Easter.Admin")){
							main.getInstance().debug();
							return true;
						}else{
							p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("NoPermissions"));
							return true;
						}
					}else if(args[0].equalsIgnoreCase("give")){
						if(p.hasPermission("Easter.Admin")){
							ItemStack is = new ItemStack(main.getInstance().m);
							ItemMeta im = is.getItemMeta();
							im.setDisplayName(variablen.msg.get("EggName"));
							is.setItemMeta(im);
							is.setAmount(64);
							p.getInventory().addItem(is);
							p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("GiveEgg"));
							return true;
						}else{
							p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("NoPermissions"));
							return true;
						}
					}else if(args[0].equalsIgnoreCase("remove")){
						if(p.hasPermission("Easter.Admin")){
							if(variablen.playerEisammler.contains(p)){
								p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("NoMoreRemoveEggs"));
								variablen.playerEisammler.remove(p);
								return true;
							}else{
								variablen.playerEisammler.add(p);
								p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("RemoveEgg"));
								return true;
							}
						}else{
							p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("NoPermissions"));
							return true;
						}
					}else if(args[0].equalsIgnoreCase("bunny")){
						if(p.hasPermission("Easter.Admin")){
							new Bunny(p.getLocation(), main.getInstance(), false);
						}else{
							p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("NoPermissions"));
							return true;
						}
					}else{
						p.sendMessage(help);
						return true;
					}
				}else if(args.length == 2){
					if(args[0].equalsIgnoreCase("setwin")){
						if(p.hasPermission("Easter.Admin")){
							if(main.getInstance().isDouble(args[1])){
								main.getInstance().Prize = Double.parseDouble(args[1]);
								p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("setWin").replace("@money", args[1]));
								
								main.getInstance().getConfig().set("DiceEasterEggs.Prize", main.getInstance().Prize);
								main.getInstance().saveConfig();
								return true;
							}else{
								p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("WinWrong"));
								return true;
							}
						}else{
							p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("NoPermissions"));
							return true;
						}
					}else if(args[0].equalsIgnoreCase("top")){
						if(p.hasPermission("Easter.Top")){
							if(main.getInstance().isInt(args[1])){
								new topTen(p, Integer.parseInt(args[1]));
								return true;
							}else if(args[1].equalsIgnoreCase("Output")){
								if(p.hasPermission("Easter.Admin")){
									new topTen(p, Integer.MAX_VALUE);
									return true;
								}else{
									p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("NoPermissions"));
									return true;
								}
							}else{
								p.sendMessage(help);
							}
						}else{
							p.sendMessage(main.getInstance().getHeader() + variablen.msg.get("NoPermissions"));
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
