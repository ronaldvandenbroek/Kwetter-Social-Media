import {Component, OnInit} from '@angular/core';
import {Kwetter} from '../../model/kwetter';
import {KwetterService} from '../../service/kwetter.service';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  kwetters: Kwetter[];

  constructor(private timelineService: KwetterService) {
  }

  ngOnInit() {
    this.timelineService.timeline().subscribe(data => {
      this.kwetters = data;
    });
  }
}
