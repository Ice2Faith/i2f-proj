import moment from "moment"

const DateUtil = {
  javaMapping: {
    "yyyy": "YYYY",
    "MM": "MM",
    "dd": "DD",
    "HH": "HH",
    "mm": "mm",
    "ss": "ss",
    "SSS": "SSS"
  },
  javaPatten2MomentPatten(patten) {
    patten = patten + ''
    Object.keys(this.javaMapping).forEach((key, index) => {
      patten = patten.replaceAll(key, this.javaMapping[key])
    })
    return patten
  },
  format(date, patten) {
    return this.date2moment(date).format(patten)
  },
  javaFormat(date, patten) {
    return this.date2moment(date).format(this.javaPatten2MomentPatten(patten))
  },
  parse(str, patten) {
    return this.moment2date(moment(str, patten))
  },
  javaParse(str, patten) {
    return this.moment2date(moment(str, this.javaPatten2MomentPatten(patten)))
  },
  date2moment(date) {
    return moment(date)
  },
  moment2date(mom) {
    return mom.toDate()
  },
  addYears(date, num) {
    let mom = this.date2moment(date).add(num, 'years')
    return this.moment2date(mom)
  },
  addMonths(date, num) {
    let mom = this.date2moment(date).add(num, 'months')
    return this.moment2date(mom)
  },
  addWeeks(date, num) {
    let mom = this.date2moment(date).add(num, 'weeks')
    return this.moment2date(mom)
  },
  addDays(date, num) {
    let mom = this.date2moment(date).add(num, 'days')
    return this.moment2date(mom)
  },
  addHours(date, num) {
    let mom = this.date2moment(date).add(num, 'hours')
    return this.moment2date(mom)
  },
  addMinutes(date, num) {
    let mom = this.date2moment(date).add(num, 'minutes')
    return this.moment2date(mom)
  },
  addSeconds(date, num) {
    let mom = this.date2moment(date).add(num, 'seconds')
    return this.moment2date(mom)
  },
  addMilliseconds(date, num) {
    let mom = this.date2moment(date).add(num, 'milliseconds')
    return this.moment2date(mom)
  }
}

export default DateUtil
