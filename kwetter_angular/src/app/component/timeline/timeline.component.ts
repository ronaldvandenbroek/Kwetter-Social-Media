import {Component, OnInit} from '@angular/core';
import {KwetterModel} from '../../model/kwetter.model';
import {KwetterService} from '../../service/kwetter.service';

@Component({
  selector: 'app-timeline',
  templateUrl: './timeline.component.html',
  styleUrls: ['./timeline.component.css']
})
export class TimelineComponent implements OnInit {

  kwetters: KwetterModel[];

  constructor(private timelineService: KwetterService) {
  }

  ngOnInit() {
    this.timelineService.timeline().subscribe(data => {
      this.kwetters = data;
    });
  }
}
