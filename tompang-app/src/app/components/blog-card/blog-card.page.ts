import { Component, Input, OnInit } from '@angular/core';
import { Blog } from 'src/app/blog/blog.page';

@Component({
  selector: 'app-blog-card',
  templateUrl: './blog-card.page.html',
  styleUrls: ['./blog-card.page.scss'],
})
export class BlogCardPage implements OnInit {

  @Input() blog: Blog;
  basePictureUrl = '../../assets/images';

  constructor() { }

  ngOnInit() {
  }

  getBlogPicture() {
    return this.basePictureUrl + this.blog.picture;
  }
}
