package toby.user.domain;

public class User {
	String id;
	String name;
	String pwd;
	String email;
	Level level;
	int login;
	int recommend;
	
	public User() {}
	
	public User(String id, String name, String pwd, String email, Level level, int login, int recommend) {
		super();
		this.id = id;
		this.name = name;
		this.pwd = pwd;
		this.email = email;
		this.level = level;
		this.login = login;
		this.recommend = recommend;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getLogin() {
		return login;
	}

	public void setLogin(int login) {
		this.login = login;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	
	public void upgradeLevel() {
		Level nextLevel = this.level.nextLevel();
		
		if(nextLevel == null) {
			throw new IllegalStateException(this.level + "은 업그레이드 불가합니다.");
		} else {
			this.level = nextLevel;
		}
	}
		
}
