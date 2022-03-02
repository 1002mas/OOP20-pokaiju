package model.monster;
public interface MonsterBuilder {

	public MonsterBuilder name(String name); 
	
	public MonsterBuilder secondName(String name);
	
	public MonsterBuilder thirdName(String name);
	
	public MonsterBuilder monsterType(MonsterType type);

	public MonsterBuilder level(int lvl);
	
	public MonsterBuilder health(int hp);
	
	public MonsterBuilder exp(int exp);
	
	public MonsterBuilder info(String info);
	
	public MonsterBuilder secondInfo(String info);
	
	public MonsterBuilder thirdInfo(String info);
	
	public Monster build();
	
}