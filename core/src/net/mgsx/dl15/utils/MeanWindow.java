package net.mgsx.dl15.utils;

import java.util.Comparator;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class MeanWindow {
	private static class Entry{
		float value;
		float time;
	}

	private static final Comparator<Entry> comparator = new Comparator<MeanWindow.Entry>() {
		
		@Override
		public int compare(Entry o1, Entry o2) {
			return Float.compare(o2.time, o1.time);
		}
	};
	
	private Pool<Entry> pool = new Pool<Entry>(){
		@Override
		protected Entry newObject() {
			return new Entry();
		}
	};
	private final Array<Entry> entries = new Array<Entry>();
	
	private float windowSize;
	
	public float value;
	public float total;
	
	public MeanWindow(float windowSize) {
		super();
		this.windowSize = windowSize;
	}

	public void put(float time, float value){
		Entry e = pool.obtain();
		e.time = time;
		e.value = value;
		entries.add(e);
		
		total += value;
	}
	
	public void update(float time){
		entries.sort(comparator);
		while(entries.size > 0 && entries.peek().time < time - windowSize){
			Entry e = entries.pop();
			pool.free(e);
		}
		float mean = 0;
		for(int i=0 ; i<entries.size ; i++){
			mean += entries.get(i).value;
		}
		
		value = mean / windowSize;
	}

	public void clear() {
		while(entries.size > 0){
			Entry e = entries.pop();
			pool.free(e);
		}
		value = 0;
		total = 0;
	}
}
