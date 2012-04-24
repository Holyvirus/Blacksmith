package com.github.Holyvirus.Blacksmith.core.Misc;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

public class Misc {
	
	public static Material getType(ItemStack i) {
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
				return i.getType();
		}
		
		return null;
	}
}
