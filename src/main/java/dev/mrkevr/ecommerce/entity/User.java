package dev.mrkevr.ecommerce.entity;

import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.REFRESH;
import static jakarta.persistence.FetchType.EAGER;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.mrkevr.ecommerce.entity.embeddable.Address;
import dev.mrkevr.ecommerce.entity.generator.GeneticEntityIdentifierGenerator;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "User")
@Table(name = "users", 
	uniqueConstraints = {
	@UniqueConstraint(columnNames = "username", name = "uk_username"),
	@UniqueConstraint(columnNames = "email", name = "uk_email")
})
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends GenericEntity implements UserDetails {

	private static final long serialVersionUID = -2268718342760256881L;
	
	@Id
	@GenericGenerator(name = "users_id_seq", type = GeneticEntityIdentifierGenerator.class)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	@Column(name = "user_id", updatable = false)
	private String id;
	
	@Column(name = "oauth2_id")
	private String oauth2Id;
	
	@Column(name = "username")
	private String username;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "address")
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "street", column = @Column(name = "street")),
			@AttributeOverride(name = "barangay", column = @Column(name = "barangay")),
			@AttributeOverride(name = "municipality", column = @Column(name = "municipality")),
			@AttributeOverride(name = "province", column = @Column(name = "province")),
			@AttributeOverride(name = "zipcode", column = @Column(name = "zipcode")) })
	private Address Address;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "password")
	private String password;

	@ManyToMany(fetch = EAGER, cascade = { DETACH, MERGE, REFRESH })
	@JoinTable(name = "users_roles", 
		joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private ShoppingCart shoppingCart;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Order> orders;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getIdPrefix() {
		return "USER";
	}
}
