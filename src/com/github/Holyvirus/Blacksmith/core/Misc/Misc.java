package com.github.Holyvirus.Blacksmith.core.Misc;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class Misc {
	
	public static ToolType getKind(ItemStack i) {
		if(i != null) {
			switch(i.getTypeId()) {
				case 298:
				case 299:
				case 300:
				case 301:
					return ToolType.LEATHER;
				case 261:
				case 268:
				case 269:
				case 270:
				case 271:
				case 290:
				case 346:
					return ToolType.WOOD;
				case 272:
				case 273:
				case 274:
				case 275:
				case 291:
					return ToolType.STONE;
				case 256:
				case 257:
				case 258:
				case 267:
				case 292:
				case 306:
				case 307:
				case 308:
				case 309:
				case 359:
					return ToolType.IRON;
				case 276:
				case 277:
				case 278:
				case 279:
				case 293:
				case 310:
				case 311:
				case 312:
				case 313:
					return ToolType.DIAMOND;
				case 283:
				case 284:
				case 285:
				case 286:
				case 294:
				case 314:
				case 315:
				case 316:
				case 317:
					return ToolType.GOLD;
				case 302:
				case 303:
				case 304:
				case 305:
					return ToolType.CHAIN;
			}
				
		}
		
		return ToolType.INVALID;
	}
	
	public static ToolType getType(ItemStack i) {
		if(i == null)
			return ToolType.INVALID;
		
		switch(i.getTypeId()) {
			case 256:
			case 269:
			case 273:
			case 277:
			case 284:
				return ToolType.SPADE;
			case 257:
			case 270:
			case 274:
			case 278:
			case 285:
				return ToolType.PICKAXE;
			case 258:
			case 271:
			case 275:
			case 279:
			case 286:
				return ToolType.AXE;
			case 290:
			case 291:
			case 292:
			case 293:
			case 294:
				return ToolType.HOE;
			case 267:
			case 268:
			case 272:
			case 276:
			case 283:
				return ToolType.SWORD;
			case 298:
			case 302:
			case 306:
			case 310:
			case 314:
				return ToolType.HELMET;
			case 299:
			case 303:
			case 307:
			case 311:
			case 315:
				return ToolType.CHEST;
			case 300:
			case 304:
			case 308:
			case 312:
			case 316:
				return ToolType.LEG;
			case 301:
			case 305:
			case 309:
			case 313:
			case 317:
				return ToolType.BOOT;
			case 261:
				return ToolType.BOW;
			case 346:
				return ToolType.ROD;
			case 259:
				return ToolType.FLINT_AND_STEEL;
			case 359:
				return ToolType.SHEARS;
		}
		
		return ToolType.INVALID;
	}
	
	public static Material getMatType(ItemStack i) {
		if(i == null)
			return null;
		
		switch(i.getTypeId()) {
			case 256:
			case 257:
			case 258:
			case 259:
			case 261:
			case 267:
			case 268:
			case 269:
			case 270:
			case 271:
			case 272:
			case 273:
			case 274:
			case 275:
			case 276:
			case 278:
			case 279:
			case 283:
			case 284:
			case 285:
			case 286:
			case 290:
			case 291:
			case 292:
			case 293:
			case 294:
			case 298:
			case 299:
			case 300:
			case 301:
			case 302:
			case 303:
			case 304:
			case 305:
			case 306:
			case 307:
			case 308:
			case 309:
			case 310:
			case 311:
			case 312:
			case 313:
			case 314:
			case 315:
			case 316:
			case 317:
			case 346:
			case 359:
				return i.getType();
		}
		
		return null;
	}
}
