import { Component, OnInit } from '@angular/core';

export interface Blog {
  title: string;
  picture: string;
  city: string;
  description: string;
}



@Component({
  selector: 'app-blog',
  templateUrl: './blog.page.html',
  styleUrls: ['./blog.page.scss'],
})
export class BlogPage implements OnInit {

  usaBlogs: Blog[];
  sgBlogs: Blog[];
  krBlogs: Blog[];
  myBlogs: Blog[];
  japanBlogs: Blog[];

  constructor() {
    this.initialiseUSABlogs();
    this.initialiseSGBlogs();
    this.initialiseKRBlogs();
    this.initialiseMYBlogs();
    this.initialiseJPBlogs();
  }

  ngOnInit() {
  }

  initialiseJPBlogs() {
    this.japanBlogs = [];
    let blog: Blog = {
      title: 'Omamori charms',
      picture: '/uploadedFiles/blogs/omamori_charms.jpg',
      city: 'Kawasaki',
      description: 'Omamori are good luck amulets sold at Shinto shrines. During exam time, ' +
      'many students can be seen with success omamori dangling from their bags.'
    };
    this.japanBlogs.push(blog);

    blog = {
      title: 'Gotochi goods',
      picture: '/uploadedFiles/blogs/gotochi_goods.jpg',
      city: 'Chiba',
      description: 'Gotochi is a word used todescribe local specialties and regional flavors, but can also  ' +
      'include things that aren’t edible, like the Hello Kitty Gotochi Series charms. These are only sold in their ' +
      'respective cities or prefectures, making them especially sought-after even ' +
      'among Japanese.'
    };
    this.japanBlogs.push(blog);

    blog = {
      title: 'Sake',
      picture: '/uploadedFiles/blogs/sake.jpg',
      city: 'Tokyo',
      description: 'Japan brews, distills and curates some of the finest liquors in the world. Be sure to check out ' +
      'local spirits, in addition to the more traditional sake and fruit wines most visitors go for—Suntory’s ' +
      'Yamazaki Single Malt was once crowned the best whisky in the world.'
    };
    this.japanBlogs.push(blog);
  }

  initialiseMYBlogs() {
    this.myBlogs = [];
    let blog: Blog = {
      title: 'Dodol',
      picture: '/uploadedFiles/blogs/dodol.jpg',
      city: 'Kuala Lumpur',
      description: 'Dodol is Malaysia’s unique, sticky-soft toffees. It is the sweet that everyone brings back as ' +
      'the truly Malaysian flavour for their friends and family. The toffee is traditionally dark in colour ' +
      'and comes in interesting flavours like Pandang and durian. Pandang is a type of leaf with a charming ' +
      'scent, while durian is a popular tropical fruit in South-east Asia that closely resembles a jackfruit. ' +
      'Dodols sold as loose souvenirs in Malaysia and in pre-packed boxes. '
    };
    this.myBlogs.push(blog);

    blog = {
      title: 'Songket',
      picture: '/uploadedFiles/blogs/songket.jpg',
      city: 'George Town',
      description: 'Songket is a traditional textile of Malaysia having roots in the Kelantan State. Trade relations ' +
      'with China and India during the 12th century gave birth to this material. It is a type of brocade that ' +
      'comes with a rich and sophisticated look because of interwoven gold and silver silk threads upon other ' +
      'colours. Pieces of various lengths can be bought of Songket and later be used to tailor salwar suits, ' +
      'blouses for saris, Kurtis, or even use as curtains and tablecloths for special occasions. '
    };
    this.myBlogs.push(blog);
  }

  initialiseKRBlogs() {
    this.krBlogs = [];
    let blog: Blog = {
      title: 'Soju (Korean Alcoholic Drink)',
      picture: '/uploadedFiles/blogs/soju.jpg',
      city: 'Seoul',
      description: 'One of the things many foreign visitors in Korea bring back home is, of course, Korean alcoholic ' +
      'drinks. “Soju and Makgeolli (Korean rice wine)” are relatively cheaper than those outside the country. ' +
      'So try to fit one of these bottles into your carrier no matter what! Recently, soju companies have ' +
      'launched sojus in fruit flavors, such as grapefruit, peach, and plum, and more! These fruit sojus are ' +
      'more popular these days because they are less bitter.'
    };
    this.krBlogs.push(blog);

    blog = {
      title: 'Gim (Dried Seaweed)',
      picture: '/uploadedFiles/blogs/seaweed.jpg',
      city: 'Busan',
      description: '“Gim”, or a paper-like dried seaweed with a bit of salt sprinkled and spread with ' +
      'sesame oil, is full of calcium, vitamins, and carotenes. Inexpensive and with relatively little ' +
      'weight, many foreign visitors love to take the delicious dried seaweed home!'
    };
    this.krBlogs.push(blog);
  }

  initialiseSGBlogs() {
    this.sgBlogs = [];
    const blog: Blog = {
      title: 'Miniature Merlion Souvenir',
      picture: '/uploadedFiles/blogs/merlion.jpg',
      city: 'Arizona',
      description: 'The iconic symbol of Singapore, Merlion is a bizarre man-made creature with the upper half '+
      'representing lion and another half the body of a mermaid. While the upper lion head represents Singapore\'s ' +
      'original name, i.e. Singapura, meaning "lion city", the lower half depicts the island\'s origin as a fishing ' +
      'village. Many souvenirs like key chains, refrigerator magnets, can openers, ashtrays, as well as chocolates ' +
      'with miniature merlion are available to choose from, at various tourist attraction areas including Chinatown.'
    };
    this.sgBlogs.push(blog);
  }

  initialiseUSABlogs() {
    this.usaBlogs = [];
    let blog: Blog = {
      title: 'Sabon Sea Salt Scrubs',
      picture: '/uploadedFiles/blogs/sabon_sea_salt_scrubs.jpg',
      city: 'Arizona',
      description: 'Having tweaked an ancient Aboriginal soap recipe, the Sabon Fragrance Shop began ' +
      'in 1973 as a backyard soap-making boutique. Now 38 years later, Sabon boasts eight Arizona ' +
      'storefronts, sprinkled through the island’s trendiest districts. Each shop is artfully decorated with ' +
      'chandeliers, brass fixtures, and a stone well. Newcomers are invited to wash their hands with their ' +
      'signature Sabon sea salts. Dark wood paneling and romantic candles make each visit to Sabon a journey ' +
      'into fragrant nirvana. In the heart of Greenwich Village, Sabon has one of its most popular shops, ' +
      'surrounded by the vibrancy and chic lifestyle of this iconic neighborhood. Cafes and boutiques line the ' +
      'streets. College students and musicians traverse the walkways. In the Village, life is hip.'
    };
    this.usaBlogs.push(blog);

    blog = {
      title: 'New York Yankees Pillow Pets',
      picture: '/uploadedFiles/blogs/yankees_pillow_pets.jpg',
      city: 'New York',
      description: 'Draping a cute and cuddly teddy bear with the iconic pinstripes of the Yankees, Pillow ' +
      'Pets gives young New York fans a plush friend to nap with on the plane ride home. The Yankee Pillow ' +
      'Pet lays flat on any surface for a thin cushion. But line up the paws and you make a pinstripe baseball ' +
      'bear to cradle tired heads. Pricing for Yankee Pillow Pets begin at $29.99. Several locations sell these ' +
      'comfy pals, including the Yankees Clubhouse Shops. With three locations in Manhattan, this sport shop ' +
      'houses thousands of Yankees memorabilia, player jerseys, and unique collectibles.'
    };
    this.usaBlogs.push(blog);

    blog = {
      title: 'Dean & Deluca Coffee',
      picture: '/uploadedFiles/blogs/dean_deluca_coffee.jpg',
      city: 'New York',
      description: 'Coffee is the lifeline for many Manhattanites and at Dean & Deluca, business is booming. Owners ' +
      'Joel Dean and Giorgio DeLuca opened their first shop in 1977 on the streets of Soho. Their plan began with ' +
      'a passion to bring artisan foods into New York City. Three decades later, Dean & Deluca have 14 retail ' +
      'stores in the United States and maintain six in Manhattan alone. Daily specials of soup and sandwiches ' +
      'draw a regular crowd. But their coffee is the superstar on the menu. Both superior in quality and a fair ' +
      'trade product, Dean & Deluca coffee is sustainably grown and produced by Counter Culture. '
    };
    this.usaBlogs.push(blog);

  }
}
