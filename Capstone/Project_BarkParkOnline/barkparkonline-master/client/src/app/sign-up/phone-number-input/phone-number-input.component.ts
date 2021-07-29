import {FocusMonitor} from '@angular/cdk/a11y';
import {coerceBooleanProperty} from '@angular/cdk/coercion';
import {
  AfterViewInit,
  Component,
  ElementRef,
  Inject,
  Input,
  OnDestroy,
  OnInit,
  Optional,
  Self,
  ViewChild
} from '@angular/core';
import {
  AbstractControl,
  ControlValueAccessor,
  FormBuilder,
  FormControl,
  FormGroup,
  NgControl,
  Validators
} from '@angular/forms';
import {MAT_FORM_FIELD, MatFormField, MatFormFieldControl} from '@angular/material/form-field';
import {Subject} from 'rxjs';
import { AppService } from 'src/app/app-service';
import { BasicUser } from '../classes/basic-user';

/** @title Form field with custom telephone number input control. */
@Component({
  selector: 'phone-number-input',
  templateUrl: 'phone-number-input.html',
})
export class PhoneNumberInput {
  form: FormGroup = new FormGroup({
    tel: new FormControl(new MyTel('', '', ''))
  });
}

/** Data structure for holding telephone number. */
export class MyTel {
  constructor(
    public area: string,
    public exchange: string,
    public subscriber: string
  ) {}
}

export function setInputFilter(textbox: Element, inputFilter: (value: string) => boolean): void {
  ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
      textbox.addEventListener(event, function(this: (HTMLInputElement | HTMLTextAreaElement) & {oldValue: string; oldSelectionStart: number | null, oldSelectionEnd: number | null}) {
          if (inputFilter(this.value)) {
              this.oldValue = this.value;
              this.oldSelectionStart = this.selectionStart;
              this.oldSelectionEnd = this.selectionEnd;
          } else if (Object.prototype.hasOwnProperty.call(this, 'oldValue')) {
              this.value = this.oldValue;
              if (this.oldSelectionStart !== null &&
                  this.oldSelectionEnd !== null) {
                  this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
              }
          } else {
              this.value = "";
          }
      });
  });
}

/** Custom `MatFormFieldControl` for telephone number input. */
@Component({
  selector: 'tel-input',
  templateUrl: 'tel-input.html',
  styleUrls: ['tel-input.css'],
  providers: [{ provide: MatFormFieldControl, useExisting: TelInput }],
  host: {
    '[class.example-floating]': 'shouldLabelFloat',
    '[id]': 'id',
  }
})
export class TelInput
  implements OnInit, ControlValueAccessor, MatFormFieldControl<MyTel>, OnDestroy, AfterViewInit {
  static nextId = 0;
  @ViewChild('area') areaInput: HTMLInputElement;
  @ViewChild('exchange') exchangeInput: HTMLInputElement;
  @ViewChild('subscriber') subscriberInput: HTMLInputElement;

  public appService: AppService;
  public user: BasicUser;
  parts: FormGroup;
  stateChanges = new Subject<void>();
  focused = false;
  controlType = 'phone-number-input';
  id = `phone-number-input-${TelInput.nextId++}`;
  onChange = (_: any) => {};
  onTouched = () => {};

  get empty() {
    const {
      value: { area, exchange, subscriber }
    } = this.parts;

    return !area && !exchange && !subscriber;
  }

  get shouldLabelFloat() {
    return this.focused || !this.empty;
  }

  @Input('aria-describedby') userAriaDescribedBy: string;

  @Input()
  get placeholder(): string {
    return this._placeholder;
  }
  set placeholder(value: string) {
    this._placeholder = value;
    this.stateChanges.next();
  }
  private _placeholder: string;

  @Input()
  get required(): boolean {
    return this._required;
  }
  set required(value: boolean) {
    this._required = coerceBooleanProperty(value);
    this.stateChanges.next();
  }
  private _required = false;

  @Input()
  get disabled(): boolean {
    return this._disabled;
  }
  set disabled(value: boolean) {
    this._disabled = coerceBooleanProperty(value);
    this._disabled ? this.parts.disable() : this.parts.enable();
    this.stateChanges.next();
  }
  private _disabled = false;

  @Input()
  get value(): MyTel | null {
    if (this.parts.valid) {
      const {
        value: { area, exchange, subscriber }
      } = this.parts;
      return new MyTel(area, exchange, subscriber);
    }
    return null;
  }
  set value(tel: MyTel | null) {
    const { area, exchange, subscriber } = tel || new MyTel('', '', '');
    this.parts.setValue({ area, exchange, subscriber });
    this.stateChanges.next();
  }

  get errorState(): boolean {
    return this.parts.invalid && this.parts.dirty;
  }

  constructor(
    formBuilder: FormBuilder,
    private _focusMonitor: FocusMonitor,
    private _elementRef: ElementRef<HTMLElement>,
    @Optional() @Inject(MAT_FORM_FIELD) public _formField: MatFormField,
    @Optional() @Self() public ngControl: NgControl,
    appService: AppService) {
    this.appService = appService;
    this.user = new BasicUser();
    this.parts = formBuilder.group({
      area: [
        null,
        [Validators.required, Validators.minLength(3), Validators.maxLength(3)]
      ],
      exchange: [
        null,
        [Validators.required, Validators.minLength(3), Validators.maxLength(3)]
      ],
      subscriber: [
        null,
        [Validators.required, Validators.minLength(4), Validators.maxLength(4)]
      ]
    });

    _focusMonitor.monitor(_elementRef, true).subscribe(origin => {
      if (this.focused && !origin) {
        this.onTouched();
      }
      this.focused = !!origin;
      this.stateChanges.next();
    });

    if (this.ngControl != null) {
      this.ngControl.valueAccessor = this;
    }
  }

  ngOnInit() {
    this.appService.login$.subscribe(user => this.user = user);
  }

  ngAfterViewInit(){
    setInputFilter(document.getElementById("area"), function(value) {
      return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
    });

    setInputFilter(document.getElementById("exchange"), function(value) {
      return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
    });

    setInputFilter(document.getElementById("subscriber"), function(value) {
      return /^\d*\.?\d*$/.test(value); // Allow digits and '.' only, using a RegExp
    });
  }

  autoFocusNext(control: AbstractControl, nextElement?: HTMLInputElement): void {
    if (!control.errors && nextElement) {
      this._focusMonitor.focusVia(nextElement, 'program');
    }
  }

  autoFocusPrev(control: AbstractControl, prevElement: HTMLInputElement): void {
    if (control.value.length < 1) {
      this._focusMonitor.focusVia(prevElement, 'program');
    }
  }

  ngOnDestroy() {
    this.stateChanges.complete();
    this._focusMonitor.stopMonitoring(this._elementRef);
  }

  setDescribedByIds(ids: string[]) {
    const controlElement = this._elementRef.nativeElement
      .querySelector('.phone-number-input-container')!;
    // controlElement.setAttribute('aria-describedby', ids.join(' '));
  }

  onContainerClick() {
    if (this.parts.controls.subscriber.valid) {
      this._focusMonitor.focusVia(this.subscriberInput, 'program');
    } else if (this.parts.controls.exchange.valid) {
      this._focusMonitor.focusVia(this.subscriberInput, 'program');
    } else if (this.parts.controls.area.valid) {
      this._focusMonitor.focusVia(this.exchangeInput, 'program');
    } else {
      this._focusMonitor.focusVia(this.areaInput, 'program');
    }
  }

  writeValue(tel: MyTel | null): void {
    this.value = tel;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  _handleInput(control: AbstractControl, nextElement?: HTMLInputElement): void {
    this.autoFocusNext(control, nextElement);
    this.onChange(this.value);
  }

  static ngAcceptInputType_disabled: boolean | string | null | undefined;
  static ngAcceptInputType_required: boolean | string | null | undefined;
}