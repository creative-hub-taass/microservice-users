package com.creativehub.backend.models;

import com.creativehub.backend.models.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private UUID id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private Role role;

	@ToString.Exclude
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "creator_id", unique = true)
	private Creator creator;

	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "followers",
			joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "following_id", referencedColumnName = "id"))
	private Set<User> inspirers = new LinkedHashSet<>();

	@ToString.Exclude
	@ManyToMany(mappedBy = "inspirers")
	private Set<User> fans = new LinkedHashSet<>();

	@Column(name = "locked", nullable = false)
	private Boolean locked = false;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled = false;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
		return List.of(authority);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@PreRemove
	public void preRemove() {
		for (User fan : fans) {
			fan.inspirers.remove(this);
		}
	}
}
