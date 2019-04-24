import { Component, OnInit } from '@angular/core';
import { User } from '../../model/user';
import { UserService } from '../../service/user.service';
 
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
 
  user: User;
 
  constructor(private userService: UserService) {
  }
 
  ngOnInit() {
    this.userService.profile().subscribe(data => {
      this.user = data;
    });
  }
}