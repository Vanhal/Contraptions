package tv.vanhal.contraptions.client.intergration;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import stanhebben.zenscript.annotations.NotNull;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

//Minetweaker Intergration
@ZenClass("tv.vanhal.contraptions.client.intergration.MTContraptions")
public class MTContraptions {

	@ZenMethod
	public static void test(@NotNull String name) {
		MineTweakerAPI.apply(new Test(name));
	}
	
	private static class Test implements IUndoableAction {
		protected String _name;
		
		public Test(String name) {
			_name = name;
		}
		
		@Override
		public void apply() {
			
		}

		@Override
		public boolean canUndo() {
			return false;
		}

		@Override
		public String describe() {
			return "Doing test "+_name;
		}

		@Override
		public String describeUndo() {
			return "Undoing Test "+_name;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public void undo() {

		}
		
	}
}
