import lejos.hardware.sensor.NXTSoundSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.SampleProvider;

/* *
* Filters sound ( like a high pass filter ) so that we do
* not hear two loud sounds within a certain amount of time
* @author Dave Cohen ( cyclingProfessor on github )
*/
public abstract class ClapFilter implements SampleProvider {
/* The loudness value that is considered to be a clap ? */
private float threshold ;
/* The time to wait after hearing a clap before reporting another */
private int timeGap ;
/* Time since last clap heard
private long lastHeard ;
/* The constructor initialises the time that the last clap was heard to be
* sufficiently far in the past that a first clap is always returned .
* The soundMode argument must be a sample provider of an NXTSoundSensor
*
* @param soundMode the sample provider for sounds
* @param clapLevel the level of sound considered to be a clap
* @param _timeGap the minimum time that must pass before a new clap
*/
long lastHeard = 0;
ClapFilter ( SensorMode soundMode , float clapLevel , int _timeGap ){
	if (! soundMode . getName (). startsWith ( " Sound " )) {
		throw new IllegalArgumentException ( " A Clap filter can only filter sound sensors " );
}
// At first say its a long time since we heard a clap
lastHeard = -2 * timeGap ;
}
/* The sample is fetched only if we have waited long enough
* If the sample is fetched and is loud then we start the wait time again from now
* The level returned is zero for no clap , and 1 for a clap .
*
* @param level the appropriate entry is filled with the resulting sample
* @param index the index to fill with the result
*/
public void fetchSample ( float level [] , int index, NXTSoundSensor ss ) {
	level [ index ] = 0; // default - no clap : false
	long now = System.currentTimeMillis();
		if ( now - lastHeard > timeGap ) {
			ss . fetchSample ( level , index );
			if ( level [ index ] >= threshold ) {
				level [ index ] = 1; // true - we heard a clap !
				lastHeard = now ;
		}
	}
}
/* The sample size is alway one since it is a boolean result . */
public int sampleSize () {
return 1;
}

}

