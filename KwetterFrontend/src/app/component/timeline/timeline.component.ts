import { Component, OnInit } from '@angular/core';
import { Kwetter } from '../../model/kwetter';
import { TimelineService } from '../../service/timeline.service';
 
@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {
 
  kwetters: Kwetter[];
 
  constructor(private timelineService: TimelineService) {
  }
 
  ngOnInit() {
    this.timelineService.timeline().subscribe(data => {
      this.kwetters = data;
    });
  }
}