import {Component, OnInit} from '@angular/core';
import {UserModel} from '../../model/user.model';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: UserModel;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.profile().subscribe(data => {
      this.user = data;
    });
  }
}
