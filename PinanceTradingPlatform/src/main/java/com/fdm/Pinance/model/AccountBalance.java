package com.fdm.Pinance.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * Represents the account balance of a user in the application.
 */
@Entity
public class AccountBalance {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "username")
    private User user;

    @Column(name = "bnb", precision = 20, scale = 4)
    private BigDecimal bnb;

    @Column(name = "btc", precision = 20, scale = 4)
    private BigDecimal btc;

    @Column(name = "doge", precision = 20, scale = 4)
    private BigDecimal doge;

    @Column(name = "eth", precision = 20, scale = 4)
    private BigDecimal eth;

    @Column(name = "xrp", precision = 20, scale = 4)
    private BigDecimal xrp;

    @Column(name = "usd", precision = 20, scale = 4)
    private BigDecimal usd;
    
    /**
     * Default constructor for the AccountBalance class.
     */
	public AccountBalance() {
		super();
	}

    /**
     * Constructor for creating an AccountBalance object with provided information.
     *
     * @param user The associated user whose account balance is being represented.
     * @param bnb  The balance of Binance Coin (BNB) for the user.
     * @param btc  The balance of Bitcoin (BTC) for the user.
     * @param doge The balance of Dogecoin (DOGE) for the user.
     * @param eth  The balance of Ethereum (ETH) for the user.
     * @param xrp  The balance of Ripple (XRP) for the user.
     * @param usd  The balance of USD (United States Dollar) for the user.
     */
	public AccountBalance(User user, BigDecimal bnb, BigDecimal btc, BigDecimal doge, BigDecimal eth,
			BigDecimal xrp, BigDecimal usd) {
		super();
		this.user = user;
		this.bnb = bnb;
		this.btc = btc;
		this.doge = doge;
		this.eth = eth;
		this.xrp = xrp;
		this.usd = usd;
	}

    /**
     * Gets the unique ID of the account balance record.
     *
     * @return The ID of the account balance record.
     */
	public Long getId() {
		return id;
	}
	
    /**
     * Sets the unique ID of the account balance record.
     *
     * @param id The ID to set for the account balance record.
     */
	public void setId(Long id) {
		this.id = id;
	}

    /**
     * Gets the associated user whose account balance is being represented.
     *
     * @return The user whose account balance is being represented.
     */
	public User getUser() {
		return user;
	}

    /**
     * Sets the associated user whose account balance is being represented.
     *
     * @param user The user to set for the account balance.
     */
	public void setUser(User user) {
		this.user = user;
	}

    /**
     * Gets the balance of Binance Coin (BNB) for the user.
     *
     * @return The balance of Binance Coin (BNB) for the user.
     */
	public BigDecimal getBnb() {
		return bnb;
	}

    /**
     * Sets the balance of Binance Coin (BNB) for the user.
     *
     * @param bnb The balance of Binance Coin (BNB) to set for the user.
     */
	public void setBnb(BigDecimal bnb) {
		this.bnb = bnb;
	}

    /**
     * Gets the balance of Bitcoin (BTC) for the user.
     *
     * @return The balance of Bitcoin (BTC) for the user.
     */
	public BigDecimal getBtc() {
		return btc;
	}

    /**
     * Sets the balance of Bitcoin (BTC) for the user.
     *
     * @param btc The balance of Bitcoin (BTC) to set for the user.
     */
	public void setBtc(BigDecimal btc) {
		this.btc = btc;
	}

    /**
     * Gets the balance of Dogecoin (DOGE) for the user.
     *
     * @return The balance of Dogecoin (DOGE) for the user.
     */
	public BigDecimal getDoge() {
		return doge;
	}

    /**
     * Sets the balance of Dogecoin (DOGE) for the user.
     *
     * @param doge The balance of Dogecoin (DOGE) to set for the user.
     */
	public void setDoge(BigDecimal doge) {
		this.doge = doge;
	}

    /**
     * Gets the balance of Ethereum (ETH) for the user.
     *
     * @return The balance of Ethereum (ETH) for the user.
     */
	public BigDecimal getEth() {
		return eth;
	}

    /**
     * Sets the balance of Ethereum (ETH) for the user.
     *
     * @param eth The balance of Ethereum (ETH) to set for the user.
     */
	public void setEth(BigDecimal eth) {
		this.eth = eth;
	}

    /**
     * Gets the balance of Ripple (XRP) for the user.
     *
     * @return The balance of Ripple (XRP) for the user.
     */
	public BigDecimal getXrp() {
		return xrp;
	}

    /**
     * Sets the balance of Ripple (XRP) for the user.
     *
     * @param xrp The balance of Ripple (XRP) to set for the user.
     */
	public void setXrp(BigDecimal xrp) {
		this.xrp = xrp;
	}

    /**
     * Gets the balance of USD (United States Dollar) for the user.
     *
     * @return The balance of USD (United States Dollar) for the user.
     */
	public BigDecimal getUsd() {
		return usd;
	}

    /**
     * Sets the balance of USD (United States Dollar) for the user.
     *
     * @param usd The balance of USD (United States Dollar) to set for the user.
     */
	public void setUsd(BigDecimal usd) {
		this.usd = usd;
	}

    /**
     * Returns a string representation of the AccountBalance object.
     *
     * @return A string containing the AccountBalance object's fields.
     */
	@Override
	public String toString() {
		return "Accounts [id=" + id + ", user=" + user + ", bnb=" + bnb + ", btc=" + btc + ", doge=" + doge + ", eth="
				+ eth + ", xrp=" + xrp + ", usd=" + usd + "]";
	}

	
    
    
}
