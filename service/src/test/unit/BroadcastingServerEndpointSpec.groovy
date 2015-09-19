import com.chookie.sense.infrastructure.BroadcastingServerEndpoint
import spock.lang.Specification
import spock.lang.Subject

import javax.websocket.RemoteEndpoint
import javax.websocket.Session


class BroadcastingServerEndpointSpec extends Specification {
    @Subject
    def endpoint = new BroadcastingServerEndpoint()

    def "should accept messages and publish toString() representation"(){
        given:
        def session = Mock(Session)
        session.isOpen() >> true

        def remoteEndpoint = Mock(RemoteEndpoint.Basic)
        session.getBasicRemote() >> remoteEndpoint

        endpoint.onOpen(session,null)

        when:
        def filter = new Expando()
        filter.toString = {'StubMessage{}'}
        endpoint.onMessage(filter)

        then:
        1 * remoteEndpoint.sendText('StubMessage{}')
    }

    def 'should forward messages to all open sessions'(){
        given:
        def remoteEndpoint = Mock(RemoteEndpoint.Basic)

        def session = Mock(Session)
        session.isOpen() >> true
        session.getBasicRemote() >> remoteEndpoint
        endpoint.onOpen(session,null)

        session = Mock(Session)
        session.isOpen() >> true
        session.getBasicRemote() >> remoteEndpoint
        endpoint.onOpen(session,null)

        def tweet = 'some tweet'

        when:
        endpoint.onMessage(tweet)

        then:
        2 * remoteEndpoint.sendText(tweet)
    }

    def 'should not try to forward to closed session'(){
        given:
        def session = Mock(Session)
        session.isOpen() >> false
        endpoint.onOpen(session,null)

        when:
        endpoint.onMessage('Some Tweet')

        then:
        0 * session./get.*remote/
    }
}