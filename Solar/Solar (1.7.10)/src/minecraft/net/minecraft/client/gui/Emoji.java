package net.minecraft.client.gui;

import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Emoji {
	public BufferedImage parseEmoji(BufferedImage sheet)
	{

		//if (img.getWidth() % 64 == 0)
		//scale = img.getWidth() / 64;
		int imageWidth = 22;
		int imageHeight = 22;
		int srcWidth = sheet.getWidth();


		BufferedImage imgNew = sheet.getSubimage(imageWidth * this.sheet_x, imageHeight * this.sheet_y, imageWidth, imageHeight);
		return imgNew;
	}


	private String name;
	private String unified;
	private String non_qualified;
	private String docomo;
	private String au;
	private String softbank;
	private String google;
	private String image;
	private int sheet_x;
	private int sheet_y;
	private String short_name;
	private String[] short_names;
	private String text;
	private String[] texts;
	private String category;
	private int sort_order;
	private String added_in;
	private boolean has_img_apple;
	private boolean has_img_google;
	private boolean has_img_twitter;
	private boolean has_img_facebook;
	private boolean has_img_messenger;
	public ResourceLocation location;
	public int renderID = -1;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnified() {
		return unified;
	}

	public void setUnified(String unified) {
		this.unified = unified;
	}

	public String getNon_qualified() {
		return non_qualified;
	}

	public void setNon_qualified(String non_qualified) {
		this.non_qualified = non_qualified;
	}

	public String getDocomo() {
		return docomo;
	}

	public void setDocomo(String docomo) {
		this.docomo = docomo;
	}

	public String getAu() {
		return au;
	}

	public void setAu(String au) {
		this.au = au;
	}

	public String getSoftbank() {
		return softbank;
	}

	public void setSoftbank(String softbank) {
		this.softbank = softbank;
	}

	public String getGoogle() {
		return google;
	}

	public void setGoogle(String google) {
		this.google = google;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getSheet_x() {
		return sheet_x;
	}

	public void setSheet_x(int sheet_x) {
		this.sheet_x = sheet_x;
	}

	public int getSheet_y() {
		return sheet_y;
	}

	public void setSheet_y(int sheet_y) {
		this.sheet_y = sheet_y;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	public String[] getShort_names() {
		return short_names;
	}

	public void setShort_names(String[] short_names) {
		this.short_names = short_names;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getTexts() {
		return texts;
	}

	public void setTexts(String[] texts) {
		this.texts = texts;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSort_order() {
		return sort_order;
	}

	public void setSort_order(int sort_order) {
		this.sort_order = sort_order;
	}

	public String getAdded_in() {
		return added_in;
	}

	public void setAdded_in(String added_in) {
		this.added_in = added_in;
	}

	public boolean isHas_img_apple() {
		return has_img_apple;
	}

	public void setHas_img_apple(boolean has_img_apple) {
		this.has_img_apple = has_img_apple;
	}

	public boolean isHas_img_google() {
		return has_img_google;
	}

	public void setHas_img_google(boolean has_img_google) {
		this.has_img_google = has_img_google;
	}

	public boolean isHas_img_twitter() {
		return has_img_twitter;
	}

	public void setHas_img_twitter(boolean has_img_twitter) {
		this.has_img_twitter = has_img_twitter;
	}

	public boolean isHas_img_facebook() {
		return has_img_facebook;
	}

	public void setHas_img_facebook(boolean has_img_facebook) {
		this.has_img_facebook = has_img_facebook;
	}

	public boolean isHas_img_messenger() {
		return has_img_messenger;
	}

	public void setHas_img_messenger(boolean has_img_messenger) {
		this.has_img_messenger = has_img_messenger;
	}
}
