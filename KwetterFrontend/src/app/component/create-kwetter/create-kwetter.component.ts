import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { KwetterService } from '../../service/kwetter.service';

@Component({
  selector: 'app-create-kwetter',
  templateUrl: './create-kwetter.component.html',
  styleUrls: ['./create-kwetter.component.css']
})
export class CreateKwetterComponent implements OnInit {
  createKwetterForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string;

  constructor(private formBuilder: FormBuilder, private route: ActivatedRoute, private router: Router,
    private createKwetterService: KwetterService) {
  }

  get f() {
    return this.createKwetterForm.controls;
  }

  ngOnInit() {
    this.createKwetterForm = this.formBuilder.group({
      text: ['', Validators.required]
    });

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/timeline';
  }

  onSubmit() {
    this.submitted = true;

    if (this.createKwetterForm.invalid) {
      return;
    }

    this.loading = true;

    this.createKwetterService.createKwetter(this.f.text.value);
    this.router.navigate([this.returnUrl]);
  }
}